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
import kotlin.reflect.KClass

/**
 * TODO - query
 */
interface RealmQuery<T : RealmObject> {

    val nativePointer: Lazy<NativePointer>

    fun execute(): RealmResults<T>

    fun count(): Number {
        return RealmInterop.realm_query_count(nativePointer.value)
    }

    fun min(): Number {
        TODO("Not yet implemented")
    }

    fun max(): Number {
        TODO("Not yet implemented")
    }

    fun sum(): Number {
        TODO("Not yet implemented")
    }

    fun average(): Number {
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
    private val results: Lazy<NativePointer>,
    private val query: String,
    private vararg val args: Any?
) : RealmQuery<T> {

    @Suppress("SpreadOperator")
    override val nativePointer: Lazy<NativePointer> =
        lazy { RealmInterop.realm_query_parse_for_results(results.value, query, *args) }

    override fun execute(): RealmResults<T> {
        val resultsType = ResultsType.FromSubQuery(results.value)
        return RealmResultsImpl(realm, clazz, schema, resultsType, query, *args)
    }
}
