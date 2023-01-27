package com.example.elandmall_kotlin.ui.search.tabs.brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.ui.search.SearchRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchBrandViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    val uiList = MutableLiveData<MutableList<SearchModule>>()
    private val moduleList = mutableListOf<SearchModule>()

    private var top10Response: List<String>? = null
    private var brandResponse: SearchBrandKeyword.NavBrandKeyword? = null

    private var alphabetList: List<String>? = null
    private var currentAlphabet: SearchBrandKeyword.NavBrandKeywordItem? = null

    init {
        requestBrand()
    }

    private fun requestBrand() {
        viewModelScope.launch {
            merge(
                repository.requestBrandTop10Stream(),
                repository.requestBrandKeywordStream()
            ).onEach {
                it.fold(
                    onSuccess = { data ->
                        if (data is SearchBrandResponse) top10Response = data.validData
                        if (data is SearchBrandKeyword) brandResponse = data.validData
                    },
                    onFailure = {}
                )
            }.onCompletion {
                if (brandResponse != null && top10Response != null) {
                    setBrandModules()

                    val selected = brandResponse?.korList?.get(0) ?: ""
                    requestBrandList(selected)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun requestBrandList(selected: String) {
        viewModelScope.launch {
            repository.requestBrandKeywordListStream(selected).collect {
                it.fold(
                    onSuccess = {
                        it?.validData?.let { data ->
                            setBrandListModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setBrandModules() {
        // top 10
        top10Response?.let { data ->
            moduleList.add(
                SearchModule(SearchModuleType.BRAND_TOP10, data)
            )
        }

        // alphabet list
        brandResponse?.let { data ->
            currentAlphabet = data.navBrandKeywordListKor?.get(0)

            moduleList.add(
                SearchModule(
                    SearchModuleType.BRAND_ALPHABETS,
                    ModuleBrandData(
                        isKorean = true,
                        savedIndex = 0,
                        list = data.korList,
                        click = { isKorean, index ->
                            alphabetList = if (isKorean) data.korList else data.engList
                            currentAlphabet =
                                if (isKorean) data.navBrandKeywordListKor?.get(index) else data.navBrandKeywordListEng?.get(index)

                            updateModules(isKorean, index, alphabetList, currentAlphabet)
                        })
                )
            )

            // brand list title
            moduleList.add(SearchModule(SearchModuleType.BRAND_LIST_TITLE, currentAlphabet))
        }
    }

    private fun setBrandListModules(data: SearchBrandKeywordList.Data) {
        // brand list
        val copiedList = moduleList.toMutableList()

        data.brandList?.forEach { brandNm ->
            copiedList.add(SearchModule(SearchModuleType.BRAND_LIST, brandNm))
        }

        uiList.postValue(copiedList)
    }

    private fun updateModules(
        isKor: Boolean,
        index: Int,
        updatedList: List<String>?,
        updatedAlphabet: SearchBrandKeyword.NavBrandKeywordItem?
    ) {
        moduleList.apply {
            map {
                when (it.type) {
                    SearchModuleType.BRAND_ALPHABETS ->
                        (it.data as? ModuleBrandData)?.apply {
                            list = updatedList
                            isKorean = isKor
                            savedIndex = index
                        }

                    SearchModuleType.BRAND_LIST_TITLE ->
                        it.data = updatedAlphabet

                    else -> {}
                }
            }
        }

        requestBrandList(this.currentAlphabet?.navBrandKeywordTitle ?: "")
    }
}