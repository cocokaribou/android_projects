package com.example.elandmall_kotlin.ui.main.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.CommonViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel : CommonViewModel() {
    private val repository: HomeRepository by lazy { HomeRepository() }

    val refreshedList = MutableLiveData<HomeResponse?>()
    override fun requestRefresh() {
        viewModelScope.launch {
            repository.requestHomeStream()
                .catch {
                    refreshedList.postValue(null)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            refreshedList.postValue(it)
                        },
                        onFailure = {
                            refreshedList.postValue(null)
                        }
                    )
                }
        }
    }

    val uiList = MutableLiveData<MutableList<ModuleData>>()
    fun setHomeModules(data: HomeResponse) {
        val moduleList = mutableListOf<ModuleData>()
        data.data?.let { homeData ->
            if (!homeData.homeMainbannerList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.CommonMainBannerData(
                        homeData.homeMainbannerList
                    )
                )
            }
            if (!homeData.homeCategoryIconList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.HomeCategoryIconData(
                        homeData.homeCategoryIconList
                    )
                )
            }
            if (!homeData.homeBannerTopList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.CommonMultiBannerData(
                        homeData.homeBannerTopList
                    )
                )
            }
            if (homeData.homeTimesale != null) {
                moduleList.add(
                    ModuleData.HomeTimeSaleData(
                        homeData.homeTimesale
                    )
                )
            }
            if (!homeData.homeBrandList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.HomeBrandData(
                        homeData.homeBrandList
                    )
                )
            }
            // title holder & goods holder
            if (homeData.homeLuckyDeal != null) {
                moduleList.add(
                    ModuleData.CommonTitleData(
                        homeData.homeLuckyDeal.title ?: "럭키딜",
                        homeData.homeLuckyDeal.subtitle ?: "서브타이틀"
                    )
                )
                if (!homeData.homeLuckyDeal.goodsList.isNullOrEmpty()) {
                    homeData.homeLuckyDeal.goodsList.forEach {
                        moduleList.add(
                            ModuleData.HomeLuckyDealData(it)
                        )
                    }
                }
            }
            // title holder & goods holder
            if (homeData.homeSeasonPlan != null) {
                moduleList.add(
                    ModuleData.CommonTitleData(
                        homeData.homeSeasonPlan.title ?: "시즌기획전",
                        homeData.homeSeasonPlan.subtitle ?: "서브타이틀"
                    )
                )

                if (!homeData.homeSeasonPlan.homeSeasonPlanList.isNullOrEmpty()) {
                    homeData.homeSeasonPlan.homeSeasonPlanList.forEach {
                        moduleList.add(
                            ModuleData.CommonPlanData(it)
                        )
                    }

                }
            }

            if (homeData.homeOfflineShop != null) {
                moduleList.add(
                    ModuleData.CommonTitleData(homeData.homeOfflineShop.title ?: "이슈 브랜드")
                )
                if (!homeData.homeOfflineShop.homeOfflineShopBanner.isNullOrEmpty() && !homeData.homeOfflineShop.homeOfflineShopList.isNullOrEmpty()) {
                    moduleList.add(
                        ModuleData.HomeStoreShopData(homeData.homeOfflineShop)
                    )
                }
            }

            if (homeData.homeMd != null) {
                moduleList.add(
                    ModuleData.CommonTitleData(
                        homeData.homeMd.title ?: "MD 추천",
                        homeData.homeMd.subtitle ?: "엄선한 추천 상품"
                    )
                )
                moduleList.add(
                    ModuleData.HomeMdRecommendData(homeData.homeMd)
                )
            }
        }
        uiList.postValue(moduleList)
    }
}