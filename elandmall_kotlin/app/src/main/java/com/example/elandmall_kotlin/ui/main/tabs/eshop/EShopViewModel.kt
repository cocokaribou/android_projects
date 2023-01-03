package com.example.elandmall_kotlin.ui.main.tabs.eshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.EshopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EShopViewModel : BaseViewModel() {
    val repository by lazy { EShopRepository() }

    val uiList = MutableLiveData<MutableList<ModuleData>>()
    private val moduleList = mutableListOf<ModuleData>()

    init {
        requestEshop()
    }

    override fun requestRefresh() {
        requestEshop()
    }

    private fun requestEshop() {
        viewModelScope.launch {
            repository.requestEshopStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let {
                                setEshopModules(it)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setEshopModules(data: EshopResponse.Data) {
        moduleList.clear()

        if (!data.eshopMainPromotionList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBannerData(data.eshopMainPromotionList)
            )
        }

        uiList.postValue(moduleList)
    }
}