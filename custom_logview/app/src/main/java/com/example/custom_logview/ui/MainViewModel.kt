package com.example.custom_logview.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { MainRepository() }

    val onApiComplete = MutableLiveData<String>()
    fun requestApis() {
        viewModelScope.launch {
            with(repository) {
                merge(
                    requestGnbData(),requestFooterData(), requestPlanShopList(), requestPlanShopListMore()
                )
                    .catch {}
                    .onEach {
                        it.fold(
                            onSuccess = {},
                            onFailure = {}
                        )
                    }
                    .onCompletion {
                        onApiComplete.postValue("merged api complete!")
                    }
                    .launchIn(viewModelScope)
            }
        }
    }
}