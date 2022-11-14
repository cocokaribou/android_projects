package com.example.elandmall_kotlin.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.ui.main.MainViewModel

open class BaseViewModel: ViewModel() {

    val isRefreshing = MutableLiveData<Boolean>()
    fun requestRefresh() {
        isRefreshing.postValue(true)
    }
}