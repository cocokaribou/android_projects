package com.example.elandmall_kotlin.ui.main.tabs.luckydeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.model.LuckyDealResponse
import com.example.elandmall_kotlin.model.LuckyDealTabResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.launch

class LuckyDealViewModel : BaseViewModel() {
    private val repository by lazy { LuckyDealRepository() }

    private val moduleList = mutableListOf<ModuleData>()
    val uiList = MutableLiveData<MutableList<ModuleData>>()

    init {
        requestLuckyDeal()
    }

    override fun requestRefresh() {
        requestLuckyDeal()
    }

    private fun requestLuckyDeal() {
        viewModelScope.launch {
            repository.requestLuckyDealStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setLuckyDealModules(data)

                                if (data.conrSetNo.isNullOrEmpty() || data.conrSetCmpsNo.isNullOrEmpty())
                                    return@collect

                                requestLuckyDealTab(
                                    conrSetNo = data.conrSetNo,
                                    conrSetCmpsNo = data.conrSetCmpsNo
                                )
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun requestLuckyDealTab(conrSetNo: String, conrSetCmpsNo: String) {
        viewModelScope.launch {
            repository.requestLuckyDealTabStream(conrSetNo, conrSetCmpsNo)
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setLuckyDealTabModules(data)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }


    private fun setLuckyDealModules(data: LuckyDealResponse.Data) {
        moduleList.clear()

        // today's lucky deal
        if (!data.newGoodsList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    titleStyle = "luckydeal",
                    title = "오늘의 럭키딜"
                )
            )
        }
        if (!data.dispCtgList.isNullOrEmpty()) {
            val list: List<Category> = data.dispCtgList.map {
                Category(image = it.img, title = it.ctgNm)
            }

            moduleList.add(
                ModuleData.CommonCategoryTabData(
                    categoryList = list
                )
            )
        }
    }

    private fun setLuckyDealTabModules(data: LuckyDealTabResponse.Data) {

    }
}