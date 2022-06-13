package com.example.shared_viewmodel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shared_viewmodel.model.MainData

/**
 */
class StoreSharedViewModel: ViewModel() {
    private val _mainList = MutableLiveData<List<MainData>>()
    val mainList : LiveData<List<MainData>> = _mainList

    fun setMainList(mainList: List<MainData>) {
        _mainList.value = mainList
    }
    private val _stoList = MutableLiveData<List<String>>()
    val stoList : LiveData<List<String>> = _stoList

    fun setStoList(stoList: List<String>) {
        _stoList.value = stoList
    }

    private val _stoContent = MutableLiveData<String>()
    val stoContent : LiveData<String> = _stoContent

    fun setStoContent(stoContent: String) {
        _stoContent.value = stoContent
    }

    val currentPage = MutableLiveData<Int>().apply { value = 0 }

    fun incPageCount() {
        currentPage.value?.let { page ->
            currentPage.value = page + 1
        }
    }
    fun decPageCount() {
        currentPage.value?.let { page ->
            if (page > 0) {
                currentPage.value = page - 1
            } else {
                currentPage.value = 0
            }
        }
    }

    fun initPageCount() {
        currentPage.value?.let {
            currentPage.value = 0
        }
    }
}