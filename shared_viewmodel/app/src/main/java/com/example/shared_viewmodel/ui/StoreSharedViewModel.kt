package com.example.shared_viewmodel.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 */
class StoreSharedViewModel: ViewModel() {

    private val _stoList = MutableLiveData<Array<String>>()
    val stoList : LiveData<Array<String>> = _stoList

    fun setStoList(stoList: Array<String>) {
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