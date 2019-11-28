package com.hari.kotlinflowsandcoroutines.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(searchRepository: SearchRepository) : ViewModel() {
    val query = MutableLiveData<String>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    val repo = query.asFlow()
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }
        .distinctUntilChanged()
        .flatMapLatest {
            searchRepository.searchRepo(it).asFlow()
        }.asLiveData()

}