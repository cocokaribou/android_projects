package com.example.elandmall_kotlin.ui.main.tabs.ekids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.DividerType
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.removeRange
import kotlinx.coroutines.launch

typealias ToggleCallback = () -> Unit
typealias ChangeCategoryCallback = (Int) -> Unit

class EKidsViewModel : BaseViewModel() {
    private val repository by lazy { EKidsRepository() }

    private var moduleList = mutableListOf<ModuleData>()

    // for tab change
    var weeklyBestList = mutableListOf<List<Goods>?>()
    var newArrivalList = mutableListOf<List<Goods>?>()
    var weeklyBestSelected = 0
    var newArrivalSelected = 0

    // for expand / collapse
    var isWeeklyExpanding = false
    var isNewExpanding = false

    val uiList = MutableLiveData<MutableList<ModuleData>>()

    init {
        requestEKids()
    }

    override fun requestRefresh() {
        requestEKids()
    }

    private fun requestEKids() {
        viewModelScope.launch {
            repository.requestEKidsStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setEKidsModules(data)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setEKidsModules(data: EKidsResponse.Data) {
        moduleList.clear()

        // main banner
        if (!data.mainBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBannerData(data.mainBanner)
            )
        }

        // category horizontal list
        if (!data.ctgList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.EKidsCategoryData(data.ctgList)
            )
        }

        // multi banner1
        if (!data.bandBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMainBannerData(
                    data.bandBanner,
                    isDividerVisible = false,
                    isIndicatorVisible = false
                )
            )
        }

        // multi banner2
        if (!data.subBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.subBanner,
                    isDividerVisible = false
                )
            )
        }

        // special deal
        data.specialDeal?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title,
                    "ekids",
                    isDividerVisible = false
                )
            )
        }
        data.specialDeal?.goodsList?.let { goodsList ->
            goodsList.forEach { goods ->
                moduleList.add(
                    ModuleData.EKidsSpecialGoodsData(goods)
                )
            }
        }

        // brand story
        data.brandStory?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title,
                    "ekids",
                    isDividerVisible = false
                )
            )
        }
        if (!data.brandStory?.bannerList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.EKidsBrandData(data.brandStory?.bannerList ?: listOf())
            )
        }

        // weekly best (md recommend)
        // changing UI
        data.weeklyBest?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title,
                    "ekids",
                    isDividerVisible = false
                )
            )
        }

        data.weeklyBest?.group?.let { ctgGroup ->
            weeklyBestSelected = 0

            val cateList: List<String> = ctgGroup.map {
                weeklyBestList.add(it.goodsList)
                it.ctgNm ?: ""
            }.toList()

            moduleList.add(
                ModuleData.EKidsRecommendCategoryData(
                    categoryList = cateList,
                    changeCategory = {
                        changeWeeklyBestTab(it)
                    },
                    initIndex = weeklyBestSelected,
                    viewType = "weeklyBest"
                )
            )

            val goodsList = ctgGroup[weeklyBestSelected].goodsList
            goodsList?.take(20)?.chunked(2)?.forEach { it ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(it)
                )
            }

            if ((goodsList?.size ?: 0) > 20) {
                moduleList.add(
                    ModuleData.EKidsExpandableData(
                        viewType = "weeklyBest",
                        toggleExpand = { toggleWeeklyBestMore() })
                )
            }
        }


        // new arrival(md new recommend)
        // changing UI
        data.newArrival?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title,
                    "ekids",
                    isDividerVisible = false
                )
            )
        }

        data.newArrival?.group?.let { newArrival ->
            newArrivalSelected = 0
            val cateList: List<String> = newArrival.map {
                newArrivalList.add(it.goodsList)

                it.ctgNm ?: ""
            }.toList()

            moduleList.add(
                ModuleData.EKidsRecommendCategoryData(
                    categoryList = cateList,
                    changeCategory = {
                        changeNewArrivalTab(it)
                    },
                    initIndex = newArrivalSelected,
                    viewType = "newArrival"
                )
            )

            val goodsList = newArrival[newArrivalSelected].goodsList
            goodsList?.take(20)?.chunked(2)?.forEach { it ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(it, DividerType.GONE)
                )
            }

            if ((goodsList?.size ?: 0) > 20) {
                moduleList.add(
                    ModuleData.EKidsExpandableData(
                        viewType = "newArrival",
                        toggleExpand = { toggleNewArrivalMore() })
                )
            }
        }

        uiList.postValue(moduleList)
    }

    private fun changeWeeklyBestTab(selected: Int) {
        weeklyBestSelected = selected
        isWeeklyExpanding = false

        val updatedGoods = weeklyBestList[weeklyBestSelected]

        val start = moduleList.indexOfFirst { it is ModuleData.CommonGoodsGridData }
        val end = moduleList.indexOfLast { it is ModuleData.EKidsRecommendCategoryData } - 2

        val updatedList = moduleList.removeGoodsHolder(start..end)

        // new ui list
        var holderCount = 0
        updatedGoods?.let { goodsList ->
            updatedList.map {
                if (it is ModuleData.EKidsRecommendCategoryData && "weeklyBest".equals(it.viewType, true)) {
                    it.initIndex = weeklyBestSelected
                }
            }
            goodsList.take(20).chunked(2).forEachIndexed { i, it ->
                holderCount++
                updatedList.add(i + start, ModuleData.CommonGoodsGridData(it, DividerType.GONE))
            }

            if (goodsList.size > 20) {
                updatedList.add(start + holderCount, ModuleData.EKidsExpandableData(
                    viewType = "weeklyBest",
                    toggleExpand = { toggleWeeklyBestMore() }))
            }
        }

        uiList.postValue(updatedList)
        moduleList = updatedList
    }

    private fun changeNewArrivalTab(selected: Int) {
        newArrivalSelected = selected
        isNewExpanding = false

        val updatedGoods = newArrivalList[newArrivalSelected]

        val start = moduleList.indexOfLast { it is ModuleData.EKidsRecommendCategoryData } + 1
        val end = moduleList.size - 1

        val updatedList = moduleList.removeGoodsHolder(start..end)

        // new ui list
        var holderCount = 0
        updatedGoods?.let { goodsList ->
            updatedList.map {
                if (it is ModuleData.EKidsRecommendCategoryData && "newArrival".equals(it.viewType, true)) {
                    it.initIndex = newArrivalSelected
                }
            }

            goodsList.take(20).chunked(2).forEachIndexed { i, it ->
                holderCount++
                updatedList.add(i + start, ModuleData.CommonGoodsGridData(it, DividerType.GONE))
            }

            if (goodsList.size > 20) {
                updatedList.add(start + holderCount, ModuleData.EKidsExpandableData(
                    viewType = "newArrival",
                    toggleExpand = { toggleNewArrivalMore() }))
            }
        }

        uiList.postValue(updatedList)
        moduleList = updatedList
    }

    private fun toggleWeeklyBestMore() {
        isWeeklyExpanding = !isWeeklyExpanding

        if (!isWeeklyExpanding) {
            // collapse
            val start = moduleList.indexOfFirst { it is ModuleData.CommonGoodsGridData }
            val end = moduleList.indexOfFirst { it is ModuleData.EKidsExpandableData }

            // leave 10 goods viewHolders
            val updatedList = moduleList.removeGoodsHolder((start + 10) until end)

            updatedList.map {
                if (it is ModuleData.EKidsExpandableData && "weeklyBest".equals(it.viewType, true)) {
                    it.isExpanded = isWeeklyExpanding
                }
            }
            uiList.postValue(updatedList)
            moduleList = updatedList

        } else {
            // expand
            val end = moduleList.indexOfFirst { it is ModuleData.EKidsExpandableData }
            val updatedList = moduleList.map { it.clone() }.toMutableList()

            val updatedGoods = weeklyBestList[weeklyBestSelected]?.toMutableList()?.removeRange(0..19)

            updatedGoods?.let { goodsList ->
                goodsList.chunked(2).forEachIndexed { i, it ->
                    updatedList.add(i + end, ModuleData.CommonGoodsGridData(it, DividerType.GONE))
                }

                updatedList.map {
                    if (it is ModuleData.EKidsExpandableData && "weeklyBest".equals(it.viewType, true)) {
                        it.isExpanded = isWeeklyExpanding
                    }
                }
            }
            uiList.postValue(updatedList)
            moduleList = updatedList

        }
    }

    private fun toggleNewArrivalMore() {
        isNewExpanding = !isNewExpanding

        if (!isNewExpanding) {
            // collapse more
            val start = moduleList.indexOfLast { it is ModuleData.EKidsRecommendCategoryData } + 1
            val end = moduleList.indexOfLast { it is ModuleData.EKidsExpandableData }

            // leave 10 goods viewHolders
            val updatedList = moduleList.removeGoodsHolder((start + 10) until end)

            updatedList.map {
                if (it is ModuleData.EKidsExpandableData && "newArrival".equals(it.viewType, true)) {
                    it.isExpanded = isNewExpanding
                }
            }
            uiList.postValue(updatedList)
            moduleList = updatedList

        } else {
            // expand more
            val end = moduleList.indexOfLast { it is ModuleData.EKidsExpandableData }
            val updatedList = moduleList.map { it.clone() }.toMutableList()

            val updatedGoods = newArrivalList[newArrivalSelected]?.toMutableList()?.removeRange(0..19)

            updatedGoods?.let { goodsList ->
                goodsList.chunked(2).forEachIndexed { i, it ->
                    updatedList.add(i + end, ModuleData.CommonGoodsGridData(it, DividerType.GONE))
                }

                updatedList.map {
                    if (it is ModuleData.EKidsExpandableData && "newArrival".equals(it.viewType, true)) {
                        it.isExpanded = isNewExpanding
                    }
                }
            }
            uiList.postValue(updatedList)
            moduleList = updatedList

        }
    }

    private fun MutableList<ModuleData>.removeGoodsHolder(range: IntRange): MutableList<ModuleData> {
        return this.removeRange(range).map { it.clone() }.toMutableList()
    }
}