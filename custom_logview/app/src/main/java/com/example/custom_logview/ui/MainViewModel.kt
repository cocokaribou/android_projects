package com.example.custom_logview.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val repository by lazy { MainRepository() }

    init {
        requestGnb()
    }

    private fun requestGnb() {
        viewModelScope.launch {
            repository.requestGnbStream()
                .collect{
                    it.fold(
                        onSuccess = {},
                        onFailure = {}
                    )
                }
        }
    }
}