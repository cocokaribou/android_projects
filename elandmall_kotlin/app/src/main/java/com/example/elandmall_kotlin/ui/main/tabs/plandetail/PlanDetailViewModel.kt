package com.example.elandmall_kotlin.ui.main.tabs.plandetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.model.PlanDetailResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx
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
    val index = MutableLiveData<Int>()

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

    private fun setPlanDetailModules(data: PlanDetailResponse.Data) {
        moduleList.clear()

        // plan shop info
        data.planShopInfo?.let { planShopInfo ->
            if (!planShopInfo.planShopNm.isNullOrEmpty()) {
                planShopName = planShopInfo.planShopNm
                moduleList.add(
                    ModuleData.CommonCenterTitleData(
                        title = planShopInfo.planShopNm,
                        titleStyle = "plan_detail",
                        isDividerVisible = true
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
        if (!data.tabList.isNullOrEmpty()) {
            tabList = data.tabList.map { it.dispCtgNm ?: "" }

            moduleList.add(
                ModuleData.CommonSortData(
                    selectSort = {
                        val pos = it as? Int ?: 0
                        index.postValue(pos)
                    },
                    selectGrid = {
                        updateGrid()
                    },
                    list = tabList,
                    isTopPaddingVisible = true
                )
            )
        }

        // tab title + goods
        // changing UI
        if (!data.goodsInfo.isNullOrEmpty()) {
            data.goodsInfo.map {
                it.tabTitle = "${it.dispCtgNm}(${it.goodsList?.size ?: 0})"
            }

            goodsInfoData = data.goodsInfo
            goodsModuleList = moduleList.map { it.clone() }.toMutableList()

            data.goodsInfo.forEachIndexed { i, tabData ->
                // for sticky ui
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
        uiList.postValue(goodsModuleList)
    }

    fun updateGrid() {
        indexList.clear()

        if (mGridNo >= 2) {
            mGridNo = 0
        } else {
            mGridNo++
        }

        goodsModuleList = moduleList.map {
            if (it is ModuleData.CommonSortData) {
                it.gridSelected = mGridNo
            }
            it.clone()
        }.toMutableList()

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
}