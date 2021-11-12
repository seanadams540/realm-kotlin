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

package io.realm.schema

// Should we try to avoid enums completely as introducing new ones breaks compatibility due to
// requirement of exhaustive `when`s. ... but exhaustive `when`s are also extremely useful to
// ensure test coverage, etc. Maybe worth following https://youtrack.jetbrains.com/issue/KT-38750
enum class CollectionType {
    NONE,
    LIST,
    //SET,
    //MAP,
}