package com.hari.kotlinflowsandcoroutines.api

import androidx.lifecycle.LiveData
import com.hari.kotlinflowsandcoroutines.utils.network.LiveDataCallAdapterFactory
import com.hari.kotlinflowsandcoroutines.utils.network.Resource
import com.hari.kotlinflowsandcoroutines.vo.RepoSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<Resource<RepoSearchResponse>>

    companion object {

        fun getGithubService(): GithubService {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
                .build()
                .create(GithubService::class.java)
        }
    }
}