package com.example.elandmall_kotlin.ui

import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse

sealed class ModuleData {
    abstract fun clone(): ModuleData

    data class HomeMainBannerData(
        val homeBannerData: List<HomeResponse.HomeMainbanner>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeCategoryIconData(
        val homeCategoryIconData: List<HomeResponse.HomeCategoryIcon>,
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeMultiBannerData(
        val homeBannerData: List<Banner>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeTimeData(
        val homeTimeData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeBrandData(
        val homeBrandData: List<HomeResponse.HomeBrand>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeTitleData(
        val title: String,
        val subTitle: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeLuckyDealData(
        val homeLuckyDealData: List<Goods>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeSeasonPlanData(
        val homeSeasonPlansData: HomeResponse.HomeSeasonPlan.HomeSeasonPlanItem
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeStoreShopData(
        val homeStoreShopData: HomeResponse.HomeOfflineShop
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

}
