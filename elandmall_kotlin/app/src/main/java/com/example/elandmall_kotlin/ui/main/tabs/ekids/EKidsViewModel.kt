package com.example.elandmall_kotlin.ui.main.tabs.ekids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class EKidsViewModel : BaseViewModel() {
    private val repository by lazy { EKidsRepository() }

    private var moduleList = mutableListOf<ModuleData>()

    var mdRecommendList = listOf<Goods>()
    var newArrivalList =listOf<Goods>()
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
        data.weeklyBest?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
                )
            )
        }

        data.weeklyBest?.group?.let { ctgGroup ->
            val cateList: List<String> = ctgGroup.map { it.ctgNm ?: "" }.toList()
            moduleList.add(
                ModuleData.EKidsRecommendCategoryData(
                    cateList
                )
            )

            ctgGroup[0].goodsList?.subList(0, 20)?.chunked(2)?.forEach { goodsList ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(
                        goodsList
                    )
                )
            }

            moduleList.add(
                ModuleData.EKidsExpandableData(
                    title = "더보기"
                )
            )
        }


        // new arrival(md new recommend)
        data.newArrival?.title?.let { title ->
            moduleList.add(
                ModuleData.CommonCenterTitleData(
                    title
                )
            )
        }

        data.newArrival?.group?.let { newArrival ->
            val cateList: List<String> = newArrival.map { it.ctgNm ?: "" }.toList()
            moduleList.add(
                ModuleData.EKidsRecommendCategoryData(
                    cateList
                )
            )

            newArrival[0].goodsList?.subList(0, 20)?.chunked(2)?.forEach { goodsList ->
                moduleList.add(
                    ModuleData.CommonGoodsGridData(
                        goodsList
                    )
                )
            }

            moduleList.add(
                ModuleData.EKidsExpandableData(
                    title = "더보기"
                )
            )
        }

        uiList.postValue(moduleList)
    }
}