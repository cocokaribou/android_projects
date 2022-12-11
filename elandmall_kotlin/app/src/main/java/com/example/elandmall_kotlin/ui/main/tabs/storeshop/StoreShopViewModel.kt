package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.StorePickResponse
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopStickyAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class StoreShopViewModel : BaseViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    // store pick ui payloads
    var mSortNo = 2
    var mSortKey = "신상품순"
    var mCateNo = ""
    var mGridNo = 0

    // category list
    var cateData = listOf<StoreShopResponse.CategoryGoods>()

    private var pickData = listOf<Goods>()
    private var pickModuleList = mutableListOf<ModuleData>()
    private val moduleList = mutableListOf<ModuleData>()

    val uiList = MutableLiveData<MutableList<ModuleData>>()

    init {
        // store shop
        requestStore()
    }

    override fun requestRefresh() {
        // refresh
        mSortNo = 2
        mSortKey = "신상품순"
        mCateNo = ""
        mGridNo = 0

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

                                cateData = data.categoryGoodsList ?: listOf()

                                // when success, call another api
                                data.storePickList?.get(0)?.categoryNo?.let {
                                    mCateNo = it
                                    requestPick(mCateNo, mSortNo, mGridNo)
                                }
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun requestPick(categoryNo: String, sortNo: Int, gridNo: Int) {
        // will not utilize param (mock data)
        Logger.v("payload! cate: $categoryNo || sort: $sortNo || grid: $gridNo")
        viewModelScope.launch {
            repository.requestStorePickStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            setStorePickModules(it?.data, gridNo)
                            storeShopCateAdapter.submitList(cateData)
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
                    ModuleData.CommonMainBannerData(
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
                    ModuleData.CommonTitleData(
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
                    ModuleData.CommonTitleData(
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
                    ModuleData.CommonTitleData(
                        title = "스토어픽 지점",
                        subTitle = "매장에서 직접 확인하고 픽업해보세요."
                    )
                )

                moduleList.add(
                    ModuleData.StorePickHeaderData(
                        storeShopData.storePickList,
                        storeSelected = storeShopData.storePickList[0].relContNm ?: ""
                    )
                )
                moduleList.add(
                    ModuleData.CommonSortData(
                        TabType.STORE_SHOP,
                        sortMap = storeSortMap,
                        includeTopPadding = false,
                        sortSelected = mSortKey,
                        gridSelected = mGridNo
                    )
                )
                // goods

                moduleList.add(
                    ModuleData.StorePickMoreData(
                        storeSelected = storeShopData.storePickList[0].relContNm ?: ""
                    )
                )
            }

            // category
            if (!storeShopData.categoryGoodsList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.CommonTitleData(
                        title = "카테고리별 베스트 상품",
                        subTitle = ""
                    )
                )
                moduleList.add(
                    ModuleData.StoreShopCateTabData(
                        storeShopData.categoryGoodsList
                    )
                )
                storeShopData.categoryGoodsList.forEachIndexed { i, data ->
                    moduleList.add(
                        ModuleData.StoreShopCateTitleData(
                            text = data.ctgNm ?: "",
                            i
                        )
                    )
                    data.goodsList?.chunked(2)?.forEach {
                        moduleList.add(
                            ModuleData.CommonGoodsGridData(it)
                        )
                    }
                }

            }
        }
        uiList.postValue(moduleList)
    }

    private fun setStorePickModules(data: StorePickResponse.Data?, gridNo: Int) {
        pickModuleList = moduleList.map { it.clone() }.toMutableList()

        val index = pickModuleList.indexOfFirst { it is ModuleData.StorePickMoreData }
        data?.let { storePickData ->
            storePickData.keywordResult?.searchGoods?.let {
                pickData = it

                drawStorePickGoods(it, gridNo)
            }
        } ?: run {
            pickModuleList.add(
                index,
                ModuleData.StoreShopEmptyGoodsData()
            )
        }
        uiList.postValue(pickModuleList)
    }

    fun updateGrid() {
        if (mGridNo >= 2) {
            mGridNo = 0
        } else {
            mGridNo++
        }

        moduleList.map {
            if (it is ModuleData.CommonSortData) {
                it.gridSelected = mGridNo
                it.sortSelected = mSortKey
            }
        }
        pickModuleList = moduleList.map { it.clone() }.toMutableList()

        drawStorePickGoods(pickData, mGridNo)
        uiList.postValue(pickModuleList)
    }

    fun updateStore(data: StoreShopResponse.StorePick) {
        mCateNo = data.categoryNo ?: return

        moduleList.map {
            if (it is ModuleData.StorePickHeaderData) {
                it.storeSelected = data.relContNm ?: ""
            }

            if (it is ModuleData.StorePickMoreData) {
                it.storeSelected = data.relContNm ?: ""
            }
        }
        uiList.postValue(moduleList)
        requestPick(categoryNo = mCateNo, sortNo = mSortNo, gridNo = mGridNo)
    }

    fun updateSort(sortClicked: String) {
        mSortKey = sortClicked
        mSortNo = storeSortMap[sortClicked] ?: 2

        moduleList.map {
            if (it is ModuleData.CommonSortData) {
                it.sortSelected = sortClicked
                it.gridSelected = mGridNo
            }
        }
        uiList.postValue(moduleList)
        requestPick(categoryNo = mCateNo, sortNo = mSortNo, gridNo = mGridNo)
    }

    private val storeSortMap: Map<String, Int> = mapOf(
        "인기상품순" to 1,
        "신상품순" to 2,
        "낮은가격순" to 3,
        "높은가격순" to 4,
        "상품평순" to 5,
        "할인율높은순" to 8,
        "추천순" to 7
    )

    private fun drawStorePickGoods(goodsList: List<Goods>, type: Int) {
        val index = pickModuleList.indexOfFirst { it is ModuleData.StorePickMoreData }
        when (type) {
            0 -> {
                goodsList.chunked(2).forEachIndexed { i, goods ->
                    pickModuleList.add(
                        i + index,
                        ModuleData.CommonGoodsGridData(goods)
                    )
                }
            }
            1 -> {
                goodsList.forEachIndexed { i, goods ->
                    pickModuleList.add(
                        i + index,
                        ModuleData.CommonGoodsLinearData(goods)
                    )
                }
            }
            2 -> {
                goodsList.forEachIndexed { i, goods ->
                    pickModuleList.add(
                        i + index,
                        ModuleData.CommonGoodsLargeData(goods)
                    )
                }
            }
        }
    }
}