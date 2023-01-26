package com.example.elandmall_kotlin.ui.search.tabs.brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.ui.search.SearchRepository
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchBrandViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    val uiList = MutableLiveData<MutableList<SearchModule>>()
    val moduleList = mutableListOf<SearchModule>()

    private var alphabetList: List<String> = listOf()
    private var brandList: SearchBrandKeywordList? = null

    private var clickedIndex = 0
    private var isKor = true
    private var currentCount: SearchBrandKeyword.NavBrandKeywordItem? = null

    private var clicker: (Boolean, Int) -> Unit = { switch, i ->
        isKor = switch
        clickedIndex = i

        updateModules(isKor, clickedIndex, currentCount)
    }

    init {
        requestMergedData()
    }

    private fun requestMergedData() {
        viewModelScope.launch {
            merge(
                repository.requestBrandKeywordStream(),
                repository.requestBrandKeywordListStream()
            ).onEach {
                it.fold(
                    onSuccess = {
                        (it as? SearchBrandKeyword)?.validData?.let { data ->
                            alphabetList = if (isKor) data.korList else data.engList
                        }
                        (it as? SearchBrandKeywordList)?.let { list ->
                            brandList = list
                        }
                    },
                    onFailure = {}
                )
            }.onCompletion {
                if (alphabetList != null && brandList != null)
                    setModules()
            }.launchIn(viewModelScope)
        }
    }

    private fun setModules() {
        moduleList.clear()

        // alphabets
        moduleList.add(
            SearchModule(
                SearchModuleType.BRAND_ALPHABETS,
                ModuleBrandData(isKorean = isKor, savedIndex = clickedIndex, data = alphabetList, clicker)
            )
        )
        // list title
        moduleList.add(SearchModule(SearchModuleType.BRAND_LIST_TITLE, currentCount))

        // list
        brandList?.validData?.keywordList?.forEach { brandNm ->
            moduleList.add(SearchModule(SearchModuleType.BRAND_LIST, brandNm))
        }

        uiList.postValue(moduleList)
    }

    private fun updateModules(isKor: Boolean, clickedIndex: Int, currentBrandList: SearchBrandKeyword.NavBrandKeywordItem?) {
        moduleList.map {
            if (it.type == SearchModuleType.BRAND_ALPHABETS) {
            }
            if (it.type == SearchModuleType.BRAND_LIST_TITLE)
                it.data = currentBrandList

        }
        uiList.postValue(moduleList)

        val list = moduleList.find { it.type == SearchModuleType.BRAND_LIST }
    }
}