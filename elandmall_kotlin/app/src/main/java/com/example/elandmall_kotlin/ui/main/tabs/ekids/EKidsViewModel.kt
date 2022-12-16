package com.example.elandmall_kotlin.ui.main.tabs.ekids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.removeRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var weeklyBestSelected = 0
var newArrivalSelected = 0

class EKidsViewModel : BaseViewModel() {
    private val repository by lazy { EKidsRepository() }

    private var moduleList = mutableListOf<ModuleData>()

    var weeklyBestList = mutableListOf<List<Goods>?>()
    var newArrivalList = mutableListOf<List<Goods>?>()

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
                ModuleData.CommonMainBannerData(
                    data.mainBanner
                )
            )
        }

        // category horizontal list
        if (!data.ctgList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.EKidsCategoryData(
                    data.ctgList
                )
            )
        }

        // multi banner1
        if (!data.bandBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.bandBanner
                )
            )
        }

        // multi banner2
        if (!data.subBanner.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.CommonMultiBannerData(
                    data.subBanner
                )
            )
        }

        // special deal
        data.specialDeal?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
                )
            )
        }
        data.specialDeal?.goodsList?.let { goodsList ->
            goodsList.forEach { goods ->
                moduleList.add(
                    ModuleData.EKidsSpecialGoodsData(
                        goods
                    )
                )
            }
        }

        // brand story
        data.brandStory?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
                )
            )
        }
        if (!data.brandStory?.bannerList.isNullOrEmpty()) {
            moduleList.add(
                ModuleData.EKidsBrandData(
                    data.brandStory?.bannerList ?: listOf()
                )
            )
        }

        // weekly best (md recommend)
        // changing UI
        data.weeklyBest?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
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
                    cateSelected = weeklyBestSelected,
                    viewType = "weeklyBest"
                )
            )

            val goodsList = ctgGroup[weeklyBestSelected].goodsList
            goodsList?.take(20)?.chunked(2)?.forEach { it ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(
                        it
                    )
                )
            }

            if ((goodsList?.size ?: 0) > 20) {
                moduleList.add(
                    ModuleData.EKidsExpandableData(
                        viewType = "weeklyBest"
                    )
                )
            }
        }


        // new arrival(md new recommend)
        // changing UI
        data.newArrival?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
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
                    cateSelected = newArrivalSelected,
                    viewType = "newArrival"
                )
            )

            val goodsList = newArrival[newArrivalSelected].goodsList
            goodsList?.take(20)?.chunked(2)?.forEach { it ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(
                        it
                    )
                )
            }

            if ((goodsList?.size ?: 0) > 20) {
                moduleList.add(
                    ModuleData.EKidsExpandableData(
                        viewType = "newArrival"
                    )
                )
            }
        }

        uiList.postValue(moduleList)
    }

    fun updateWeeklyBest(selected: Int) {
        weeklyBestSelected = selected
        val updatedGoods = weeklyBestList[weeklyBestSelected]

        val start = moduleList.indexOfFirst { it is ModuleData.CommonGoodsGridData }
        val end = moduleList.indexOfFirst { it is ModuleData.EKidsExpandableData }

        val updatedList = moduleList.removeRange(start..end).map { it.clone() }.toMutableList()

        updatedGoods?.let { goodsList ->
            goodsList.take(20).chunked(2).forEachIndexed { i, it ->
                updatedList.add(i + start, ModuleData.CommonGoodsGridData(it))
            }

            if (goodsList.size > 20) {
                updatedList.add(end, ModuleData.EKidsExpandableData(viewType = "weeklyBest"))
            }
        }

//        if (it is ModuleData.EKidsRecommendCategoryData && "weeklyBest".equals(it.viewType, true)) {
//            it.cateSelected = weeklyBestSelected
//        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            uiList.postValue(updatedList)
        }
    }

    fun updateNewArrival(selected: Int) {
        newArrivalSelected = selected
        val updatedGoods = newArrivalList[newArrivalSelected]

        val start = moduleList.indexOfLast { it is ModuleData.EKidsRecommendCategoryData } + 1
        val end = moduleList.indexOfLast { it is ModuleData.EKidsExpandableData }

        val updatedList = moduleList.removeRange(start..end).map { it.clone() }.toMutableList()

        updatedGoods?.let { goodsList ->
            goodsList.take(20).chunked(2).forEachIndexed { i, it ->
                updatedList.add(i + start, ModuleData.CommonGoodsGridData(it))
            }

            if (goodsList.size > 20) {
                updatedList.add(end, ModuleData.EKidsExpandableData(viewType = "newArrival"))
            }
        }

        //if (it is ModuleData.EKidsRecommendCategoryData && "newArrival".equals(it.viewType, true)) {
        //                it.cateSelected = newArrivalSelected
        //            }

        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            uiList.postValue(updatedList)
        }
    }

    fun expandWeeklyBest() {

    }

    fun expandNewArrival() {

    }
}