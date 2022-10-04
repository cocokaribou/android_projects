package com.youngin.lunch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngin.lunch.model.Store
import com.youngin.lunch.model.StoreDataList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { MainRepository() }

    val mainData = MutableLiveData<StoreDataList?>()
    fun getListData() {
        viewModelScope.launch {
            repository.requestStoreListStream()
                .map {
                    it.getOrNull().apply {
                        this?.let {
                            stoList?.forEach {
                                it.stoImgUrl = "https://app-dl.pionnet.co.kr/store_img/${it.stoImgUrl}"
                            }
                            stoList?.shuffle()
                        }
                    }
                }
                .collect {
                    mainData.postValue(it)
                }
        }
    }

}