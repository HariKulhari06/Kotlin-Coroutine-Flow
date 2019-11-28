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

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A generic class that can provide a resource backed the network.
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 *
 * @param <RequestType>*/
abstract class NetworkBoundResource<RequestType> {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        setValue(Resource.loading())
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            response.apply {
                if (status.isSuccessful()) {
                    setValue(
                        Resource(
                            NetworkState.SUCCESS,
                            this.data
                        )
                    )
                } else {
                    onFetchFailed()
                    setValue(
                        Resource.error(
                            errorMessage
                        )
                    )
                }
            }

        }
    }

    private fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<RequestType>>

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>
}