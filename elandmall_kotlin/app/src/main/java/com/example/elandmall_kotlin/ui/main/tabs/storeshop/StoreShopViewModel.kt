package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
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

    var clicked = 0
    val clicker: () -> Unit = {
       if (clicked <= 2) {
           clicked = 0
       }
       else {
           clicked++
       }
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
                    ModuleData.CategoryHorizontalData(
                        storeShopData.recommendStoreList
                    )
                )
            }

            if (storeShopData.myRegularStoreList != null) {
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
                        clicker
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
                Logger.v("뭐셈 ${storeShopData.categoryGoodsList}")
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
}