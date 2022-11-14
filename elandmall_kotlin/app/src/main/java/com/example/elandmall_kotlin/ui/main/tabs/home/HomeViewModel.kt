package com.example.elandmall_kotlin.ui.main.tabs.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.BaseViewModel
import com.example.elandmall_kotlin.ui.ModuleData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    init {

    }

    val repository: HomeRepository by lazy { HomeRepository() }
    val homeData = MutableLiveData<HomeResponse?>()
    fun requestHome() {
        viewModelScope.launch {
            repository.requestHomeStream()
                .catch {
                    homeData.postValue(null)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            homeData.postValue(it)
                        },
                        onFailure = {
                            homeData.postValue(null)
                        }
                    )
                }
        }
    }

    val homeList = MutableLiveData<MutableList<ModuleData>>()

    fun setHomeModules(data: HomeResponse) {
        val moduleList = mutableListOf<ModuleData>()
        data.data?.let { homeData ->
            when {
                !homeData.homeMainbannerList.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeMainBannerData(
                            homeData.homeMainbannerList
                        )
                    )
                }
                !homeData.homeCategoryIconList.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeCategoryIconData(
                            homeData.homeCategoryIconList
                        )
                    )
                }
                !homeData.homeBannerTopList.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeMultiBannerData(
                            homeData.homeBannerTopList
                        )
                    )
                }
                !homeData.homeTimesale.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeTimeData(
                            homeData.homeTimesale
                        )
                    )
                }
                !homeData.homeBrandList.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeBrandData(
                            homeData.homeBrandList
                        )
                    )
                }
                // title holder & goods holder
                !homeData.homeLuckyDeal.isNullOrEmpty() -> {
                    moduleList.add(
                        ModuleData.HomeTitleData(
                            homeData.homeLuckyDeal[0].title ?: "럭키딜",
                            homeData.homeLuckyDeal[0].subtitle ?: "서브타이틀"
                        )
                    )
                    homeData.homeLuckyDeal.forEach { goods ->
                        moduleList.add(
                            ModuleData.HomeLuckyDealData(goods)
                        )
                    }
                }
                // title holder & goods holder
                homeData.homeSeasonPlan != null -> {
                    moduleList.add(
                        ModuleData.HomeTitleData(
                            homeData.homeSeasonPlan.title ?: "시즌기획전",
                            homeData.homeSeasonPlan.subtitle ?: "서브타이틀"
                        )
                    )

                    homeData.homeSeasonPlan.homeSeasonPlanList?.let { seasonList ->
                        seasonList.forEach {
                            moduleList.add(
                                ModuleData.HomeSeasonPlansData(it)
                            )
                        }
                    }
                }

                homeData.homeOfflineShop != null -> {
                    if (homeData.homeOfflineShop.homeOfflineShopBanner.isNullOrEmpty() && homeData.homeOfflineShop.homeOfflineShopList.isNullOrEmpty()) {
                    } else {
                        moduleList.add(
                            ModuleData.HomeStoreShopData(homeData.homeOfflineShop)
                        )

                    }
                }


                else -> {}

            }
        }
        homeList.postValue(moduleList)
    }
}