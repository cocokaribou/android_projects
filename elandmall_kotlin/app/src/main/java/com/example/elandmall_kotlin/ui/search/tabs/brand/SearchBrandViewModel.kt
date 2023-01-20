package com.example.elandmall_kotlin.ui.search.tabs.brand

import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.ui.search.SearchRepository

class SearchBrandViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    init {

    }
    suspend fun requestMergedData() {
        repository.requestBrandStream()
    }

}