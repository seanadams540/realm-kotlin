/*
 * Copyright 2020 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.internal

import io.realm.RealmObject
import io.realm.RealmQuery
import io.realm.RealmQueryImpl
import io.realm.RealmResults
import io.realm.internal.interop.Callback
import io.realm.internal.interop.Link
import io.realm.internal.interop.NativePointer
import io.realm.internal.interop.RealmInterop
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

// FIXME API-QUERY Final query design is tracked in https://github.com/realm/realm-kotlin/issues/84
//  - Lazy API makes it harder to debug
//  - Postponing execution to actually accessing the elements also prevents query parser errors to
//    be raised. Maybe we can get an option to pre-validate queries in the C-API?

/**
 * TODO : query
 */
sealed class ResultsType {
    object FromFirstQuery : ResultsType()
    class FromSubQuery(val resultsPointer: NativePointer) : ResultsType()
    class FromExistingResults(val resultsPointer: NativePointer) : ResultsType()
}

/**
 * TODO : query - lazy loading, etc.
 *
 * @param query
 * @param args
 */
internal class RealmResultsImpl<T : RealmObject> constructor(
    private val realm: RealmReference,
    private val clazz: KClass<T>,
    private val schema: Mediator,
    resultsType: ResultsType,
    private val query: String,
    private vararg val args: Any?
) : AbstractList<T>(), RealmResults<T>, RealmStateHolder, Observable<RealmResults<T>> {

    private val resultsPointer: Lazy<NativePointer> = lazy { fetchResults(resultsType) }

    override val size: Int
        get() = RealmInterop.realm_results_count(resultsPointer.value).toInt()

    override fun get(index: Int): T {
        val link: Link = RealmInterop.realm_results_get<T>(resultsPointer.value, index.toLong())
        val model: RealmObjectInternal = schema.createInstanceOf(clazz)
            .also { it.link(realm, schema, clazz, link) }
        @Suppress("UNCHECKED_CAST")
        return model as T
    }

    override fun query(query: String, vararg args: Any?): RealmResultsImpl<T> {
        val resultsType = ResultsType.FromSubQuery(resultsPointer.value)
        return RealmResultsImpl(realm, clazz, schema, resultsType, query, args)
    }

    override fun filter(query: String, vararg args: Any?): RealmQuery<T> =
        RealmQueryImpl(realm, clazz, schema, resultsPointer, query, *args)

    override fun observe(): Flow<RealmResults<T>> {
        realm.checkClosed()
        return realm.owner.registerObserver(this)
    }

    override fun delete() {
        // TODO OPTIMIZE Are there more efficient ways to do this? realm_query_delete_all is not
        //  available in C-API yet, but should probably await final query design
        //  https://github.com/realm/realm-kotlin/issues/84
        RealmInterop.realm_results_delete_all(resultsPointer.value)
    }

    /**
     * Returns a frozen copy of this query result. If it is already frozen, the same instance
     * is returned.
     */
    override fun freeze(frozenRealm: RealmReference): RealmResultsImpl<T> =
        getResultsForNotification(frozenRealm)

    /**
     * Thaw the frozen query result, turning it back into a live, thread-confined RealmResults.
     */
    override fun thaw(liveRealm: RealmReference): RealmResultsImpl<T> =
        getResultsForNotification(liveRealm)

    override fun registerForNotification(callback: Callback): NativePointer =
        RealmInterop.realm_results_add_notification_callback(resultsPointer.value, callback)

    override fun emitFrozenUpdate(
        frozenRealm: RealmReference,
        change: NativePointer,
        channel: SendChannel<RealmResults<T>>
    ): ChannelResult<Unit> {
        val frozenResult = freeze(frozenRealm)
        return channel.trySend(frozenResult)
    }

    override fun realmState(): RealmState = realm

    // Lazily builds linked query and creates results pointer
    @Suppress("SpreadOperator")
    private fun fetchResults(resultsType: ResultsType): NativePointer {
        // Set results directly when freezing/thawing results inside notifier
        if (resultsType is ResultsType.FromExistingResults) {
            return resultsType.resultsPointer
        }

        return when (resultsType) {
            // Parse original query when creating first results
            is ResultsType.FromFirstQuery -> RealmInterop.realm_query_parse(
                realm.dbPointer,
                clazz.simpleName!!,
                query,
                *args
            )
            // Link query to existing results when making subqueries
            is ResultsType.FromSubQuery -> RealmInterop.realm_query_parse_for_results(
                resultsType.resultsPointer,
                query,
                *args
            )
            else -> throw IllegalArgumentException("Invalid results type: $resultsType")
        }.let { query ->
            RealmInterop.realm_query_find_all(query)
        }
    }

    private fun getResultsForNotification(realm: RealmReference): RealmResultsImpl<T> {
        val results = RealmInterop.realm_results_resolve_in(resultsPointer.value, realm.dbPointer)
        val resultsType = ResultsType.FromExistingResults(results)
        return RealmResultsImpl(realm, clazz, schema, resultsType, query, args)
    }
}
