package com.example.shared_viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 */
class BaseViewModel: ViewModel() {

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
}