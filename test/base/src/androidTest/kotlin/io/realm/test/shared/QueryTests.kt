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

package io.realm.test.shared

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.entities.Sample
import io.realm.test.platform.PlatformUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QueryTests {

    lateinit var tmpDir: String
    lateinit var configuration: RealmConfiguration
    lateinit var realm: Realm

    @BeforeTest
    fun setup() {
        tmpDir = PlatformUtils.createTempDir()
        configuration = RealmConfiguration.Builder(schema = setOf(Sample::class))
            .path("$tmpDir/default.realm").build()
        realm = Realm.open(configuration)
    }

    @AfterTest
    fun tearDown() {
        if (!realm.isClosed()) {
            realm.close()
        }
        PlatformUtils.deleteTempDir(tmpDir)
    }

    @Test
    fun playground() {
        val results: RealmResults<Sample> = realm.objects(Sample::class)
        val query: RealmQuery<Sample> = results.filter("stringField == $0", "Realm")
        val count: Number = query.count()
        assertEquals(0L, count)

        realm.writeBlocking {
            copyToRealm(Sample())
            copyToRealm(Sample().apply {
                stringField = "ASDF"
                intField = 666
            })
        }

        // Query without filtering
        realm.objects(Sample::class)
            .filter()
            .count()
            .let { assertEquals(2L, it) }

        // Query by stringField value
        realm.objects(Sample::class)
            .filter("stringField == $0", "Realm")
            .count()
            .let { assertEquals(1L, it) }

        // Query by stringField value again
        realm.objects(Sample::class)
            .filter("stringField == $0", "ASDF")
            .count()
            .let { assertEquals(1L, it) }

        // Wrong query returns no results
        realm.objects(Sample::class)
            .filter("stringField == $0", "Wrong!")
            .count()
            .let { assertEquals(0L, it) }

        // Lazy evaluation at the very end
        realm.objects(Sample::class)
            .filter()
            .execute()
            .let { assertEquals(2, it.size) }

        realm.objects(Sample::class)
            .filter("stringField == $0", "Wrong!")
            .execute()
            .let { assertTrue(it.isEmpty()) }

        realm.objects(Sample::class)
            .filter("stringField == $0", "Realm")
            .filter("intField == $0", 666)
            .execute()
            .let { assertEquals(1, it.size) }


        fun returnAvgValue(): Number {
            return realm.objects(Sample::class)
                .filter("intField > $0", 42)
                .average(Sample::intField)
        }

        fun flowable(): Flow<Int> {
            return realm.objects(Sample::class)
                .filter("intField > $0", 42)
//                .max(Sample::intField)
                .observe()
                .map { it: RealmResults<Sample> ->
//                    it.max()
                    2
                }
        }

        realm.writeBlocking {
            this.objects(Sample::class)
                .filter()
                .average(Sample::intField)
                .executeScalar()
        }

        runBlocking {
            flowable().collect { maxValue: Int ->

            }
        }

        fun returnObjectWithMaxValueAsFlow(): Flow<RealmResults<Sample>> {
            val q: RealmQuery<Sample> = realm.objects(Sample::class)
                .filter("intField.@max")
            val min: Number = q.min()

            //
            val minValue: Number = realm.objects(Sample::class)
                .filter("intField.@max")
                .min()

            val avg: Number = realm.objects(Sample::class)
                .filter()
                .average(Sample::intField)
        }


    }
}
