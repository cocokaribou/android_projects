package com.example.elandmall_kotlin.ui.search.tabs.popular

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.SearchModule
import com.example.elandmall_kotlin.model.SearchModuleType
import com.example.elandmall_kotlin.model.SearchPlanShopResponse
import com.example.elandmall_kotlin.model.SearchPopularResponse
import com.example.elandmall_kotlin.ui.search.SearchRepository
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchPopularViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    val uiList = MutableLiveData<MutableList<SearchModule>>()

    var popularData: SearchPopularResponse? = null
    var planShopData: SearchPlanShopResponse? = null

    init {
        requestMergedData()
    }

    private fun requestMergedData() {
        viewModelScope.launch {
            merge(
                repository.requestPopularStream(),
                repository.requestPlanShopStream()
            ).onEach {
                it.fold(
                    onSuccess = { data ->
                        (data as? SearchPopularResponse)?.let { popular ->
                            popularData = popular
                        }
                        (data as? SearchPlanShopResponse)?.let { planShop ->
                            planShopData = planShop
                        }
                    },
                    onFailure = {}
                )
            }.onCompletion {
                if (popularData != null && planShopData != null)
                    setModules()
            }.launchIn(viewModelScope)
        }
    }

    fun setModules() {
        val list = mutableListOf<SearchModule>()

        popularData?.let {
            list.add(SearchModule(SearchModuleType.POPULAR_RANKING, it.validData))
        }

        planShopData?.let {
            list.add(SearchModule(SearchModuleType.POPULAR_PLAN_SHOP, it.validData))
        }

        uiList.postValue(list)
    }
}