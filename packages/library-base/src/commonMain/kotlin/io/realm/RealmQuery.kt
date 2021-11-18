/*
 * Copyright 2021 Realm Inc.
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
 *
 */

package io.realm

import io.realm.internal.Mediator
import io.realm.internal.RealmReference
import io.realm.internal.RealmResultsImpl
import io.realm.internal.ResultsType
import io.realm.internal.interop.NativePointer
import io.realm.internal.interop.RealmInterop
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

/**
 * TODO - query
 */
interface Filterable<T : RealmObject> {
    fun filter(query: String = "TRUEPREDICATE", vararg args: Any?): RealmQuery<T>
}

/**
 * TODO - query
 */
interface RealmQuery<T : RealmObject> : Filterable<T> {

    val nativePointer: Lazy<NativePointer>

    fun execute(): RealmResults<T>

    fun count(): Number {
        return RealmInterop.realm_query_count(nativePointer.value)
    }

    fun min(): Number {
        TODO("Not yet implemented")
    }

    fun <S> max(property: KProperty1<T, S>): Number {
        TODO("Not yet implemented")
    }

    fun sum(): Number {
        TODO("Not yet implemented")
    }

//    fun <S> average(property: KProperty1<T, S>): Number {
//        TODO("Not yet implemented")
//    }

    fun <S> average(property: KProperty1<T, S>): RealmQuery<T> {
        TODO("Not yet implemented")
    }

    fun observe(): Flow<RealmResults<T>> {
        TODO("Not yet implemented")
    }
}

/**
 * TODO - query
 */
internal class RealmQueryImpl<T : RealmObject> constructor(
    private val realm: RealmReference,
    private val clazz: KClass<T>,
    private val schema: Mediator,
    private val resultsPointer: Lazy<NativePointer>,
    private val query: String,
    private vararg val args: Any?
) : RealmQuery<T> {

    @Suppress("SpreadOperator")
    override val nativePointer: Lazy<NativePointer> =
        lazy { RealmInterop.realm_query_parse_for_results(resultsPointer.value, query, *args) }

    override fun execute(): RealmResults<T> {
        val resultsType = ResultsType.FromSubQuery(resultsPointer.value)
        return RealmResultsImpl(realm, clazz, schema, resultsType, query, *args)
    }

    override fun filter(query: String, vararg args: Any?): RealmQuery<T> {
        RealmQueryImpl(realm, clazz, schema, resultsPointer, query, args)
        TODO("Not yet implemented")
    }
}
