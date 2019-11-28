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