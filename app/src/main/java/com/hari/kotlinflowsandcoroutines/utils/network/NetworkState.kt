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