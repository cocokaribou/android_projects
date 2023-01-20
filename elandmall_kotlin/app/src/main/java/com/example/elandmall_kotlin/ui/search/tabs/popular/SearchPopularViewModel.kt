package com.example.elandmall_kotlin.ui.search.tabs.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.SearchPlanShopResponse
import com.example.elandmall_kotlin.model.SearchPopularResponse
import com.example.elandmall_kotlin.ui.search.SearchRepository
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class SearchPopularViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    init {

    }

    suspend fun requestMergedData() {
        viewModelScope.launch {
            merge(
                repository.requestPopularStream(),
                repository.requestPlanShopStream()
            ).collect {
                it.fold(
                    onSuccess = {
                        when (it) {
                            is SearchPopularResponse -> {}
                            is SearchPlanShopResponse -> {}
                        }
                    },
                    onFailure = {}
                )
            }
        }

    }

}