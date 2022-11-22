package com.example.elandmall_kotlin.ui.main.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    val repository: HomeRepository by lazy { HomeRepository() }

    val refreshedData = MutableLiveData<HomeResponse?>()
    override fun requestRefresh() {
        viewModelScope.launch {
            repository.requestHomeStream()
                .catch {
                    refreshedData.postValue(null)
                    isSuccess.postValue(false)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            refreshedData.postValue(it)
                            isSuccess.postValue(true)
                        },
                        onFailure = {
                            refreshedData.postValue(null)
                            isSuccess.postValue(false)
                        }
                    )
                }
        }
    }

    val homeList = MutableLiveData<MutableList<ModuleData>>()

    fun setHomeModules(data: HomeResponse) {
        val moduleList = mutableListOf<ModuleData>()
        data.data?.let { homeData ->
            if (!homeData.homeMainbannerList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.HomeMainBannerData(
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
                    ModuleData.HomeMultiBannerData(
                        homeData.homeBannerTopList
                    )
                )
            }
            if (homeData.homeTimesale != null) {
                moduleList.add(
                    ModuleData.HomeTimeData(
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
                    ModuleData.HomeTitleData(
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
                    ModuleData.HomeTitleData(
                        homeData.homeSeasonPlan.title ?: "시즌기획전",
                        homeData.homeSeasonPlan.subtitle ?: "서브타이틀"
                    )
                )

                if (!homeData.homeSeasonPlan.homeSeasonPlanList.isNullOrEmpty()) {
                    homeData.homeSeasonPlan.homeSeasonPlanList.forEach {
                        moduleList.add(
                            ModuleData.HomeSeasonPlanData(it)
                        )
                    }

                }
            }

            if (homeData.homeOfflineShop != null) {
                moduleList.add(
                    ModuleData.HomeTitleData(
                        homeData.homeOfflineShop.title ?: "이슈 브랜드",
                        homeData.homeOfflineShop.subtitle ?: ""
                    )
                )
                if (!homeData.homeOfflineShop.homeOfflineShopBanner.isNullOrEmpty() && !homeData.homeOfflineShop.homeOfflineShopList.isNullOrEmpty()) {
                    moduleList.add(
                        ModuleData.HomeStoreShopData(homeData.homeOfflineShop)
                    )
                }
            }

            if (homeData.homeMd != null) {
                moduleList.add(
                    ModuleData.HomeTitleData(
                        homeData.homeMd.title ?: "MD 추천",
                        homeData.homeMd.subtitle ?: "엄선한 추천 상품"
                    )
                )
                moduleList.add(
                    ModuleData.HomeMdData(homeData.homeMd)
                )
            }
        }
        homeList.postValue(moduleList)
    }
}