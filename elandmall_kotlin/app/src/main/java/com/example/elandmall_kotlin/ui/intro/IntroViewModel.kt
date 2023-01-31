package com.example.elandmall_kotlin.ui.intro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.BaseViewModel
import com.example.elandmall_kotlin.common.ApiResult
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.MainGnbResponse
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IntroViewModel : BaseViewModel() {
    var startTime: Long = 0L
    var completeTime: Long = 0L

    private val repository by lazy { IntroRepository() }

    init {
        requestIntroApis()
    }

    val mainFlag = MutableLiveData<ApiResult>()
    private fun requestIntroApis() {
        viewModelScope.launch {
            startTime = System.currentTimeMillis()
            merge(repository.requestGnbStream(), repository.requestHomeStream())
                .catch {
                    mainFlag.postValue(ApiResult.EXCEPTION)
                }
                .onEach {
                    it.fold(
                        onSuccess = {
                            if (it is MainGnbResponse) {
                                MemDataSource.mainGnbCache = it
                            } else if (it is HomeResponse) {
                                MemDataSource.homeCache = it
                            }
                        },
                        onFailure = {
                            mainFlag.postValue(ApiResult.FAIL)
                        }
                    )
                }
                .onCompletion {
                    if (MemDataSource.mainGnbCache != null && MemDataSource.homeCache != null) {
                        mainFlag.postValue(ApiResult.SUCCESS)
                        completeTime = System.currentTimeMillis() - startTime
                    } else {
                        mainFlag.postValue(ApiResult.FAIL)
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}