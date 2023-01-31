package com.example.elandmall_kotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.BaseViewModel
import com.example.elandmall_kotlin.common.ApiResult

open class CommonViewModel: BaseViewModel() {
    open fun requestRefresh() {}
}