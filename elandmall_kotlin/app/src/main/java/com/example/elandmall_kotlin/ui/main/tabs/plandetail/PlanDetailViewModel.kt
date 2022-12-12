package com.example.elandmall_kotlin.ui.main.tabs.plandetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.PlanDetailResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.launch

class PlanDetailViewModel : BaseViewModel() {
    private val repository: PlanDetailRepository by lazy { PlanDetailRepository() }

    var planShopName = ""
    var tabList = listOf<String>()
    var mGridNo = 0

    var indexList = mutableListOf<Int>()
    var goodsInfoData = listOf<PlanDetailResponse.GoodsInfo>()
    private var goodsModuleList = mutableListOf<ModuleData>()
    private var moduleList = mutableListOf<ModuleData>()

    val uiList = MutableLiveData<MutableList<ModuleData>>()

    init {
        requestPlanDetail()
    }

    override fun requestRefresh() {
        requestPlanDetail()
    }

    private fun requestPlanDetail() {
        viewModelScope.launch {
            repository.requestPlanDetailStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setPlanDetailModules(data)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setPlanDetailModules(data: PlanDetailResponse.Data?) {
        moduleList.clear()
        data?.let { planDetailData ->
            // plandetail top webview
            planDetailData.planShopInfo?.let { planShopInfo ->
                if (!planShopInfo.planShopNm.isNullOrEmpty()) {
                    planShopName = planShopInfo.planShopNm
                    moduleList.add(
                        ModuleData.CommonCenterTitleData(
                            title = planShopInfo.planShopNm
                        )
                    )
                }
                if (planShopInfo.bannerInfo?.htmlCont != null) {
                    moduleList.add(
                        ModuleData.CommonWebViewData(
                            contentHtml = planShopInfo.bannerInfo.htmlCont,
                            contentUrl = planShopInfo.shareUrl,
                            shareUrl = planShopInfo.shareUrl,
                            shareImgPath = planShopInfo.shareImgPath
                        )
                    )
                }
            }

            // common sort
            if (!planDetailData.tabList.isNullOrEmpty()) {
                tabList = planDetailData.tabList.map { it.dispCtgNm ?: "" }

                var i = 0
                val map = tabList.associateWith { i++ }
                moduleList.add(
                    ModuleData.CommonSortData(
                        TabType.PLAN_DETAIL,
                        map,
                        includeTopPadding = true,
                        sortSelected = tabList[0],
                        gridSelected = mGridNo
                    )
                )
            }

            // tab title + goods
            // changing UI
            if (!planDetailData.goodsInfo.isNullOrEmpty()) {
                planDetailData.goodsInfo.map {
                    it.tabTitle = "${it.dispCtgNm}(${it.goodsList?.size ?: 0})"
                }

                goodsInfoData = planDetailData.goodsInfo
                goodsModuleList = moduleList.map { it.clone() }.toMutableList()

                planDetailData.goodsInfo.forEachIndexed { i, tabData ->
                    indexList.add(i)
                    goodsModuleList.add(
                        ModuleData.PlanDetailTabTitleData(
                            title = tabData.tabTitle,
                        )
                    )

                    tabData.goodsList?.let { goodsList ->
                        goodsList.chunked(2).forEach {
                            indexList.add(i)
                            goodsModuleList.add(
                                ModuleData.CommonGoodsGridData(
                                    goodsListData = it
                                )
                            )
                        }
                    }
                }
            }
        }
        uiList.postValue(goodsModuleList)
    }

    fun updateGrid() {
        indexList.clear()
        if (mGridNo >= 2) {
            mGridNo = 0
        } else {
            mGridNo++
        }

        goodsModuleList = moduleList.map { it.clone() }.toMutableList()

        goodsInfoData.forEachIndexed { i, data ->
            indexList.add(i)
            goodsModuleList.add(
                ModuleData.PlanDetailTabTitleData(
                    data.tabTitle
                )
            )

            data.goodsList?.let { goodsList ->
                when (mGridNo) {
                    0 -> {
                        goodsList.chunked(2).forEach {
                            indexList.add(i)
                            goodsModuleList.add(
                                ModuleData.CommonGoodsGridData(it)
                            )
                        }
                    }
                    1 -> {
                        goodsList.forEach {
                            indexList.add(i)
                            goodsModuleList.add(
                                ModuleData.CommonGoodsLinearData(it)
                            )
                        }
                    }
                    2 -> {
                        goodsList.forEach {
                            indexList.add(i)
                            goodsModuleList.add(
                                ModuleData.CommonGoodsLargeData(it)
                            )
                        }
                    }
                }

                uiList.postValue(goodsModuleList)
            }
        }
    }

    fun updateSort() {

    }
}