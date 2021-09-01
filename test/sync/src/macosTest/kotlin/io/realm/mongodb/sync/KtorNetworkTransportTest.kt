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
 */

package io.realm.mongodb.sync

import io.realm.mongodb.sync.KtorNetworkTransport.Companion.ERROR_IO
import io.realm.test.platform.PlatformUtils
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val BASE_URL = "http://127.0.0.1:8888" // URL to command server

internal class KtorNetworkTransportTest {

    private lateinit var transport: KtorNetworkTransport

    enum class HTTPMethod(val nativeKey: String) {
        GET("get"),
        POST("post"),
        PATCH("patch"),
        PUT("put"),
        DELETE("delete")
    }

    @BeforeTest
    fun setUp() {
        transport = KtorNetworkTransport(
            dispatcher = getDispatcher(),
            timeoutMs = 5000
        )
    }

    @Test
    fun requestSuccessful() {
        val url = "$BASE_URL/okhttp?success=true"

        for (method in HTTPMethod.values()) {
            val body = if (method == HTTPMethod.GET) "" else "{ \"body\" : \"some content\" }"
            val headers = mapOf(
                Pair("Content-Type", "application/json;charset=utf-8"),
                Pair("Accept", "application/json")
            )

            val response = transport.sendRequest(
                method.nativeKey,
                url,
                headers,
                body
            )
            println("------------> METHOD: $method")
            assertEquals(200, response.httpResponseCode)
            assertEquals(0, response.customResponseCode)
            assertEquals("${method.name}-success", response.body)
            println("------------> METHOD: $method SUCCESSFUL")
        }
    }

    @Test
    fun requestFailedOnServer() {
        val url = "$BASE_URL/okhttp?success=false"
        for (method in HTTPMethod.values()) {
            val body = if (method == HTTPMethod.GET) "" else "{ \"body\" : \"some content\" }"
            val headers = mapOf(
                Pair("Content-Type", "application/json;charset=utf-8"),
                Pair("Accept", "application/json")
            )

            val response = transport.sendRequest(
                method.nativeKey,
                url,
                headers,
                body
            )
            assertEquals(500, response.httpResponseCode)
            assertEquals(0, response.customResponseCode)
            assertEquals("${method.name}-failure", response.body)
        }
    }

    // Make sure that the client doesn't crash if attempting to send invalid JSON
    // This is mostly a guard against Java crashing if ObjectStore serializes the wrong
    // way by accident.
    @Test
    fun requestSendsIllegalJson() {
        val url = "$BASE_URL/okhttp?success=true"
        for (method in HTTPMethod.values()) {
            val body = if (method == HTTPMethod.GET) "" else "Boom!"
            val headers = mapOf(
                Pair("Content-Type", "application/json;charset=utf-8"),
                Pair("Accept", "application/json")
            )

            val response: Response = transport.sendRequest(
                method.nativeKey,
                url,
                headers,
                body
            )
            assertEquals(200, response.httpResponseCode)
            assertEquals(0, response.customResponseCode)
            assertEquals("${method.name}-success", response.body)
        }
    }

    @Test
    fun requestInterrupted() {
        val url = "$BASE_URL/okhttp?success=true"
        for (method in HTTPMethod.values()) {
            val body = if (method == HTTPMethod.GET) "" else "{ \"body\" : \"some content\" }"
            val headers = mapOf(
                Pair("Content-Type", "application/json;charset=utf-8"),
                Pair("Accept", "application/json")
            )

            PlatformUtils.interrupt()

            val response: Response = transport.sendRequest(
                method.nativeKey,
                url,
                headers,
                body
            )
            assertEquals(0, response.httpResponseCode)
            assertEquals(ERROR_IO, response.customResponseCode)
            assertTrue(response.body.contains("interrupted"))
        }
    }
}