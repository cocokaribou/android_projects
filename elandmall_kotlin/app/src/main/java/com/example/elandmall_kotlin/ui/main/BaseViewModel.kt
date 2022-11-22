package com.example.elandmall_kotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.common.ApiResult

open class BaseViewModel: ViewModel() {

    val isSuccess = MutableLiveData<Boolean>()
    open fun requestRefresh() {}
}