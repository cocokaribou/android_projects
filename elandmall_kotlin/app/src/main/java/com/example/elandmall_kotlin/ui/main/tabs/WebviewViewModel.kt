package com.example.elandmall_kotlin.ui.main.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.ui.main.BaseViewModel

class WebviewViewModel : BaseViewModel() {
    override val refreshComplete = MutableLiveData<String>()
}