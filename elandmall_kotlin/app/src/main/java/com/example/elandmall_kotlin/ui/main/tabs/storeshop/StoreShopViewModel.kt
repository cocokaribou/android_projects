package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.StorePickResponse
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.CommonViewModel
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopStickyAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class StoreShopViewModel : CommonViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    // store pick ui payloads
    var sortNo = 1
    var sortSelected = ""
    var cateSelected = ""
    var gridSelected = 0

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
        sortNo = 1
        cateSelected = ""
        gridSelected = 0

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
                                    cateSelected = it
                                    requestPick(cateSelected, sortNo, gridSelected)
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
                    ModuleData.CommonTitleData(title = "추천 지점")
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
                    ModuleData.CommonTitleData(title = "나의 단골매장")
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
                        selectStore = {
                            (it as? StoreShopResponse.StorePick)?.let { store ->
                                updateStore(
                                    store,
                                    storeShopData.storePickList.indexOf(store)
                                )
                            }
                        },
                        list = storeShopData.storePickList,
                        initIndex = 0
                    )
                )
                moduleList.add(
                    ModuleData.CommonSortData(
                        selectSort = {
                            val pos = it as? Int ?: 0
                            updateSort(pos, storeSortMap.keys.toList()[pos])
                        },
                        selectGrid = {
                            updateGrid()
                        },
                        list = storeSortMap.keys.toList(),
                        isTopPaddingVisible = false,
                        sortSelected = 1
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
                    ModuleData.CommonTitleData(title = "카테고리별 베스트 상품")
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

    private fun updateGrid() {
        if (gridSelected >= 2) {
            gridSelected = 0
        } else {
            gridSelected++
        }

        moduleList.map {
            if (it is ModuleData.CommonSortData) {
                it.gridSelected = gridSelected
                it.sortSelected = sortNo
            }
        }
        pickModuleList = moduleList.map { it.clone() }.toMutableList()

        drawStorePickGoods(pickData, gridSelected)
        uiList.postValue(pickModuleList)
    }

    private fun updateStore(store: StoreShopResponse.StorePick, initIndex: Int) {
        cateSelected = store.categoryNo ?: return

        moduleList.map {
            if (it is ModuleData.StorePickHeaderData) {
                it.initIndex = initIndex
            }

            if (it is ModuleData.StorePickMoreData) {
                it.storeSelected = store.relContNm ?: ""
            }
        }
        uiList.postValue(moduleList)
        val sort = storeSortMap[sortSelected] ?: 1
        requestPick(categoryNo = cateSelected, sortNo = sort, gridNo = gridSelected)
    }

    private fun updateSort(sortNo: Int, sortKey: String) {
        this.sortNo = sortNo

        moduleList.map {
            if (it is ModuleData.CommonSortData) {
                it.sortSelected = sortNo
                it.gridSelected = gridSelected
            }
        }
        uiList.postValue(moduleList)

        sortSelected = sortKey
        val sort = storeSortMap[sortKey] ?: 1
        requestPick(categoryNo = cateSelected, sortNo = sort, gridNo = gridSelected)
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