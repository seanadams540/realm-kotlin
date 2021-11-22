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

package io.realm.internal.interop

// FIXME NAMING I think the consistent naming should be the OS names, Class/Property, but guess we
//  need to prefix it with Core/Capi to avoid Class
data class Table(
    val name: String,
    val primaryKey: String?,
    val numProperties: Long,
    val numComputerProperties: Long = 0,
    val key: ClassKey = INVALID_CLASS_KEY,
    val flags: Int = ClassFlags.RLM_CLASS_NORMAL
) {
    companion object {
        // Convenience wrapper to ease maintaining compiler plugin
        fun create(name: String, primaryKey: String?, numProperties: Long): Table {
            return Table(name, primaryKey, numProperties, 0)
        }
    }
}
