package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.StorePickResponse
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.removeRange
import kotlinx.coroutines.launch

class StoreShopViewModel : BaseViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    override val refreshComplete = MutableLiveData<String>()

    var mSortNo = 2
    var mCateNo = ""
    val cateList = MutableLiveData<List<StoreShopResponse.CategoryGoods>?>()

    private val moduleList: MutableList<ModuleData> = mutableListOf()
    val uiList = MutableLiveData<MutableList<ModuleData>>()

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
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setStoreShopModules(data)

                                cateList.postValue(data.categoryGoodsList)

                                // when success, call another api
                                data.storePickList?.get(0)?.categoryNo?.let {
                                    mCateNo = it
                                    requestPick(mCateNo, mSortNo)
                                }
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun requestPick(category: String, sort: Int) {
        // will not utilize param (mock data)
        viewModelScope.launch {
            repository.requestStorePickStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            setStorePickModules(it?.data)
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setStoreShopModules(data: StoreShopResponse.Data?) {
        moduleList.clear()
        data?.let { storeShopData ->
            // main banner
            if (!storeShopData.storeMainbannerList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.MainBannerData(
                        storeShopData.storeMainbannerList
                    )
                )
            }

            // banjjak delivery
            if (storeShopData.banjjakDeli != null) {
                moduleList.add(
                    ModuleData.StoreShopDeliveryData(
                        storeShopData.banjjakDeli
                    )
                )
            }

            // recommend offline shop
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

            // regular
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

            // store pick
            if (!storeShopData.storePickList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.TitleData(
                        title = "스토어픽 지점",
                        subTitle = "매장에서 직접 확인하고 픽업해보세요."
                    )
                )

                moduleList.add(
                    ModuleData.StoreShopPickHeaderData(
                        storeShopData.storePickList,
                        selected = storeShopData.storePickList[0].categoryNo!!
                    )
                )
                moduleList.add(
                    ModuleData.GoodsSortData(
                        goodsSortMap = storeShopSort,
                    )
                )
                moduleList.add(
                    ModuleData.StoreShopPickMoreData(
                        text = storeShopData.storePickList[0].relContNm + " 바로가기 >"
                    )
                )
            }

            // category
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
                    it.goodsList?.chunked(2)?.forEach {
                        moduleList.add(
                            ModuleData.GoodsMultiGridData(it)
                        )
                    }
                }

            }
        }
        refreshComplete.postValue("storepick")
        uiList.postValue(moduleList)
    }

    private fun setStorePickModules(data: StorePickResponse.Data?) {
        val index = moduleList.indexOfFirst { it is ModuleData.StoreShopPickMoreData }
        data?.let { storePickData ->
            if (storePickData.keywordResult?.searchGoods.isNullOrEmpty()) {
                // empty storepick
                moduleList.add(
                    index,
                    ModuleData.StoreShopEmptyGoodsData()
                )
            } else {
                storePickData.keywordResult?.searchGoods?.let {
                    pickList = it
                    it.toMutableList().chunked(2).reversed().forEach {
                        moduleList.add(
                            index,
                            ModuleData.GoodsMultiGridData(it)
                        )
                    }
                }
            }
            uiList.postValue(moduleList)
        } ?: run {
            // empty storepick
            moduleList.add(
                index,
                ModuleData.StoreShopEmptyGoodsData()
            )
            uiList.postValue(moduleList)
        }
    }

    private lateinit var pickList: List<Goods>
    fun updateGrid(gridClicked: Int) {
        val start = moduleList.indexOfFirst { it is ModuleData.GoodsSortData } + 1
        val end = moduleList.indexOfFirst { it is ModuleData.StoreShopPickMoreData }
        moduleList.removeRange(start until end)

        val index = moduleList.indexOfFirst { it is ModuleData.StoreShopPickMoreData }
        when (gridClicked) {
            0 -> {
                pickList.chunked(2).reversed().forEach {
                    moduleList.add(
                        index,
                        ModuleData.GoodsMultiGridData(it)
                    )
                }
            }
            1 -> {
                pickList.reversed().forEach {
                    moduleList.add(
                        index,
                        ModuleData.GoodsLinearData(it)
                    )
                }
            }
            2 -> {
                pickList.reversed().forEach {
                    moduleList.add(
                        index,
                        ModuleData.GoodsLargeData(it)
                    )
                }
            }
        }
        uiList.postValue(moduleList)
    }

    fun updateStore(categoryNo: String) {
        mCateNo = categoryNo
//        moduleList.map {
//            if (it is ModuleData.StoreShopPickHeaderData) {
//                it.selected = categoryNo
//            }
//        }
        requestPick(category = mCateNo, sort = mSortNo)
    }

    fun updateSort(key: String) {
        mSortNo = storeShopSort[key] ?: 2
        requestPick(category = key, sort = mSortNo)
    }

    private val storeShopSort: Map<String, Int> = mapOf(
        "인기상품순" to 1,
        "신상품순" to 2,
        "낮은가격순" to 3,
        "높은가격순" to 4,
        "상품평순" to 5,
        "할인율높은순" to 8,
        "추천순" to 7
    )
}