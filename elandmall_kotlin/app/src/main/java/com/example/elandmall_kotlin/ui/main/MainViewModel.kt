package com.example.elandmall_kotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {
    val isScrollingDown = MutableLiveData<Boolean>()
}