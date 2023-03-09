package com.example.shared_viewmodel.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var webFragmentCount = 0
    val webFragArgument = MutableLiveData<Int>()

    val doHomeRefresh = MutableLiveData<Boolean>()
}