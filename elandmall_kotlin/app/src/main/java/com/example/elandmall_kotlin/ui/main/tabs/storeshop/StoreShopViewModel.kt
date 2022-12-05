package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StoreShopViewModel : BaseViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    override val refreshComplete = MutableLiveData<String>()

    var storeResult: StoreShopResponse.StoreData? = null
    val storeShopList = MutableLiveData<StoreShopResponse.StoreData?>()
    val cateList = MutableLiveData<List<StoreShopResponse.CategoryGoods>>()

    init {
        // store shop
        requestStore()
    }

    override fun requestRefresh() {
        // refresh
        requestStore()
    }

    private fun requestStore() {
        viewModelScope.launch {
            repository.requestStoreShopStream()
                .catch {
                    storeShopList.postValue(null)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            // when success, call another api
                            it?.data?.let { data ->
                                storeResult = data
                                data.storePickList?.get(0)?.categoryNo?.let {
                                    requestPick(it)
                                }
                            } ?: run {
                                storeShopList.postValue(null)
                            }
                        },
                        onFailure = {
                            storeShopList.postValue(null)
                        }
                    )
                }
        }
    }

    private fun requestPick(ctgNo: String) {
        viewModelScope.launch {
            // mock data
            repository.requestStorePickStream()
                .catch {
                    storeShopList.postValue(null)
                    refreshComplete.postValue("storeshop")
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            // 최종 data
                            storeResult?.storePickList?.map { before ->
                                before.goodsList = it?.data?.keywordResult?.searchGoods!!
                            }

                            // sticky
                            storeResult?.categoryGoodsList?.let {
                                cateList.postValue(it)
                            }

                            storeShopList.postValue(storeResult)
                            refreshComplete.postValue("storeshop")
                        },
                        onFailure = {
                            storeShopList.postValue(null)
                            refreshComplete.postValue("storeshop")
                        }
                    )
                }
        }
    }

    var gridClicked = 0
    val gridClick: () -> Unit = {
        if (gridClicked <= 2) {
            gridClicked = 0
        } else {
            gridClicked++
        }
    }
    var sortClicked = 2
    val sortClick: (Int) -> Unit = {
        sortClicked = it
    }
    val uiList = MutableLiveData<MutableList<ModuleData>>()
    fun setStoreShopModules(data: StoreShopResponse.StoreData?) {
        val moduleList = mutableListOf<ModuleData>()
        data?.let { storeShopData ->
            if (!storeShopData.storeMainbannerList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.MainBannerData(
                        storeShopData.storeMainbannerList
                    )
                )
            }
            if (storeShopData.banjjakDeli != null) {
                moduleList.add(
                    ModuleData.StoreShopDeliveryData(
                        storeShopData.banjjakDeli
                    )
                )
            }

            if (!storeShopData.recommendStoreList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.TitleData(
                        title = "추천 지점",
                        subTitle = ""
                    )
                )
                moduleList.add(
                    ModuleData.StoreShopRecommendData(
                        storeShopData.recommendStoreList
                    )
                )
            }

            if (storeShopData.myRegularStoreList != null) {
                moduleList.add(
                    ModuleData.TitleData(
                        title = "나의 단골매장",
                        subTitle = ""
                    )
                )
                moduleList.add(
                    ModuleData.StoreShopRegularData(
                        storeShopData.myRegularStoreList
                    )
                )
            }

            if (!storeShopData.storePickList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.TitleData(
                        title = "스토어픽 지점",
                        subTitle = "매장에서 직접 확인하고 픽업해보세요."
                    )
                )

                moduleList.add(
                    ModuleData.StoreShopPickData(
                        storeShopData.storePickList,
                        gridClick
                    )
                )
                moduleList.add(
                    ModuleData.GoodsSortData(
                        goodsSortMap = storeShopSort,
                        sortClick
                    )
                )
                if (!storeShopData.storePickList.isNullOrEmpty()) {
                    storeShopData.storePickList[0].goodsList.chunked(2).forEach {
                        moduleList.add(
                            ModuleData.GoodsMultiGridData(it)
                        )
                    }
                }
                moduleList.add(
                    ModuleData.StoreShopPickMoreData(
                        text = storeShopData.storePickList[0].relContNm + " 바로가기 >"
                    )
                )
            }

            if (!storeShopData.categoryGoodsList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.StoreShopCateTabData(
                        storeShopData.categoryGoodsList
                    )
                )
                storeShopData.categoryGoodsList.forEach {
                    moduleList.add(
                        ModuleData.StoreShopCateNameData(
                            text = it.ctgNm ?: "",
                            includeDivider = false
                        )
                    )

//                    when(clicked) {
//                        0 -> {
//                            Logger.v("그리드")
                    it.goodsList?.chunked(2)?.forEach {
                        moduleList.add(
                            ModuleData.GoodsMultiGridData(it)
                        )
                    }
//                        }
//                        1 -> {
//                            Logger.v("리니어")
//                            it.goodsList?.forEach {
//                                moduleList.add(
//                                    ModuleData.GoodsLinearData(it)
//                                )
//                            }
//                        }
//                        2 -> {
//                            Logger.v("라지")
//                            it.goodsList?.forEach {
//                                moduleList.add(
//                                    ModuleData.GoodsLargeData(it)
//                                )
//                            }
//                        }
//                    }

                }

            }
        }
        uiList.postValue(moduleList)
    }

    val storeShopSort: Map<String, Int> = mapOf(
        "인기상품순" to 1,
        "신상품순" to 2,
        "낮은가격순" to 3,
        "높은가격순" to 4,
        "상품평순" to 5,
        "할인율높은순" to 8,
        "추천순" to 7
    )
}