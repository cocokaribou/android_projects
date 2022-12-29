package com.example.elandmall_kotlin.ui.main.tabs.luckydeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.model.LuckyDealResponse
import com.example.elandmall_kotlin.model.LuckyDealTabResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

typealias CategoryPayloadCallback = (List<String>) -> Unit
class LuckyDealViewModel : BaseViewModel() {
    private val repository by lazy { LuckyDealRepository() }

    private val moduleList = mutableListOf<ModuleData>()
    private var tabModuleList = mutableListOf<ModuleData>()
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
        if (!data.newGoodsList.isNullOrEmpty()) {
            data.newGoodsList.forEach {
                moduleList.add(
                    ModuleData.HomeLuckyDealData(it)
                )
            }
        }
        if (!data.dispCtgList.isNullOrEmpty()) {
            val list: List<Category> = data.dispCtgList.map {
                Category(image = it.img, title = it.ctgNm, payload1 = it.conrSetNo, payload2 = it.conrSetCmpsNo)
            }

            moduleList.add(
                ModuleData.CommonCategoryTabData(
                    categoryList = list,
                    changeCategory = { payloads ->
                        changeCategoryTab(payloads[0], payloads[1])
                    }
                )
            )
        }
        uiList.postValue(moduleList)
    }

    // TODO paging
    private fun setLuckyDealTabModules(data: LuckyDealTabResponse.Data) {
        tabModuleList = moduleList.map { it.clone() }.toMutableList()

        data.goodsInfo?.goodsList?.let { goodsList ->
            goodsList.chunked(2).forEach { goods ->
                tabModuleList.add(
                    ModuleData.CommonGoodsGridData(goods)
                )
            }
        }

        uiList.postValue(tabModuleList)
    }

    private fun changeCategoryTab(cateNo: String, cateCmpsNo: String) {
        requestLuckyDealTab(cateNo, cateCmpsNo)
    }
}