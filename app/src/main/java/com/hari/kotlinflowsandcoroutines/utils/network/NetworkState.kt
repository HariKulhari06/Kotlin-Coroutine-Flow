/*
 *  Copyright 2019  Hari Singh Kulhari
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

package com.hari.kotlinflowsandcoroutines.utils.network

/**
 * NetworkState of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class NetworkState {
    SUCCESS,
    ERROR,
    LOADING;

    /**
     * Returns `true` if the [NetworkState] is success else `false`.
     */
    fun isSuccessful() = this == SUCCESS

    /**
     * Returns `true` if the [NetworkState] is loading else `false`.
     */
    fun isLoading() = this == LOADING

    /**
     * Returns `true` if the [NetworkState] is loading else `false`.
     */
    fun isFailed() = this == ERROR

}