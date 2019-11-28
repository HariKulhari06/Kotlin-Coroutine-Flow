package com.hari.kotlinflowsandcoroutines.ui.search

import androidx.lifecycle.LiveData
import com.hari.kotlinflowsandcoroutines.api.GithubService
import com.hari.kotlinflowsandcoroutines.utils.network.NetworkBoundResource
import com.hari.kotlinflowsandcoroutines.utils.network.Resource
import com.hari.kotlinflowsandcoroutines.vo.RepoSearchResponse

class SearchRepository(val githubService: GithubService) {

    fun searchRepo(q: String): LiveData<Resource<RepoSearchResponse>> {
        return object : NetworkBoundResource<RepoSearchResponse>() {
            override fun createCall(): LiveData<Resource<RepoSearchResponse>> {
                return githubService.searchRepos(q)
            }
        }.asLiveData()
    }
}