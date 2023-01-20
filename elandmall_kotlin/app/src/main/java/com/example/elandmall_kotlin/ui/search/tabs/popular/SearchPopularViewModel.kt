package com.example.elandmall_kotlin.ui.search.tabs.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.SearchModule
import com.example.elandmall_kotlin.model.SearchModuleType
import com.example.elandmall_kotlin.model.SearchPlanShopResponse
import com.example.elandmall_kotlin.model.SearchPopularResponse
import com.example.elandmall_kotlin.ui.search.SearchRepository
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class SearchPopularViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    val uiList = MutableLiveData<MutableList<SearchModule>>()
    init {
        requestMergedData()
    }

    private fun requestMergedData() {
        viewModelScope.launch {
            merge(
                repository.requestPopularStream(),
                repository.requestPlanShopStream()
            ).collect {
                it.fold(
                    onSuccess = { data ->
                        setModules(data)
                    },
                    onFailure = {}
                )
            }
        }
    }
    fun setModules(data: Any?) {
        val list = mutableListOf<SearchModule>()

        (data as? SearchPopularResponse)?.let {
            list.add(SearchModule(SearchModuleType.POPULAR_RANKING, it.validData))
        }

        (data as? SearchPlanShopResponse)?.let {
            list.add(SearchModule(SearchModuleType.POPULAR_PLAN_SHOP, it.validData))
        }

        uiList.postValue(list)
    }
}