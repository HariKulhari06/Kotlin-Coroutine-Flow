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