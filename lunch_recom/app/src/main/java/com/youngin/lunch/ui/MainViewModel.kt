package com.youngin.lunch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youngin.lunch.model.MainData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository by lazy { MainRepository() }

    init {
        getMainData()
    }

    val mainData = MutableLiveData<MainData?>()
    private fun getMainData() {
        viewModelScope.launch {
            repository.requestMainStream()
                .map {
                    it.apply {
                        it.getOrNull()?.todayRecommend?.let {
                            it.stoImgUrl = "https://app-dl.pionnet.co.kr/store_img/${it.stoImgUrl}"
                        }
                    }
                    it.getOrNull()
                }
                .collect {
                    mainData.postValue(it)
                }
        }
    }

}