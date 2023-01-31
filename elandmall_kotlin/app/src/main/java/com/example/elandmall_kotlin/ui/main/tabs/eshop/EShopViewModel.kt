package com.example.elandmall_kotlin.ui.main.tabs.eshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.EshopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.CommonViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor

class EShopViewModel : CommonViewModel() {
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

        if (!data.eshopSubBannerList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.eshopSubBannerList,
                    isDividerVisible = false,
                )
            )
        }

        data.eshopNowIssue?.let { now ->
            moduleList.add(
                ModuleData.CommonTitleData(now.title ?: "Modern House")
            )

            val cateList:List<String> = now.group?.map { it.tabTitle ?: "" } ?: listOf()
            moduleList.add(
                ModuleData.EshopCategoryData(cateList)
            )

            if (!now.group.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.CommonMultiBannerData(listOf(now.group[0].planshopList!!))
                )
            }


        }

        uiList.postValue(moduleList)
    }
}