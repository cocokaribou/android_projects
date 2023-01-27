package com.example.elandmall_kotlin.ui.search.tabs.brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.ui.search.SearchRepository
import kotlinx.coroutines.launch

class SearchBrandViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }

    val uiList = MutableLiveData<MutableList<SearchModule>>()
    val moduleList = mutableListOf<SearchModule>()

    private var alphabetList: List<String>? = null
    private var currentAlphabet: SearchBrandKeyword.NavBrandKeywordItem? = null

    init {
        requestBrandAlphabet()
    }

    private fun requestBrandAlphabet() {
        viewModelScope.launch {
            repository.requestBrandKeywordStream().collect {
                it.fold(
                    onSuccess = {
                        it?.validData?.let { data ->
                            setAlphabetModules(data)

                            val selected = data.korList[0]

                            requestBrandList(selected)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun requestBrandList(selected: String) {
        viewModelScope.launch {
            repository.requestBrandKeywordListStream(selected).collect {
                it.fold(
                    onSuccess = {
                        it?.validData?.let { data ->
                            setListModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setAlphabetModules(data: SearchBrandKeyword.NavBrandKeyword) {
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

        moduleList.add(SearchModule(SearchModuleType.BRAND_LIST_TITLE, currentAlphabet))
    }

    private fun setListModules(data: SearchBrandKeywordList.Data) {
        val copiedList = moduleList.toMutableList()

        data.brandList?.forEach { brandNm ->
            copiedList.add(SearchModule(SearchModuleType.BRAND_LIST, brandNm))
        }

        uiList.postValue(copiedList)
    }

    private fun updateModules(isKor: Boolean, index: Int, updatedList: List<String>?, updatedAlphabet: SearchBrandKeyword.NavBrandKeywordItem?) {
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