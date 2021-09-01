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

package io.realm.test.platform

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

actual class RunLoopThread : CoroutineScope {
    actual fun run(block: RunLoopThread.() -> Unit) {
        TODO()
    }

    actual fun terminate() {
        TODO()
    }

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")
}