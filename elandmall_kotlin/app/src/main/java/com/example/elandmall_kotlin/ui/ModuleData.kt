package com.example.elandmall_kotlin.ui

import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.StoreShopResponse

sealed class ModuleData {
    abstract fun clone(): ModuleData

    data class MainBannerData(
        val mainBannerData: List<Banner>
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

    data class CommonTitleData(
        val title: String,
        val subTitle: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeLuckyDealData(
        val homeLuckyDealData: Goods
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

    data class HomeMdData(
        val homeMdData: HomeResponse.HomeMd
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopDeliveryData(
        val storeShopDeliveryData: StoreShopResponse.BanjjakDeli
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopRecommendData(
        val storeShopRecommendData: List<StoreShopResponse.RecommendStore>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopRegularData(
        val storeShopRegularData: List<StoreShopResponse.MyRegularStore>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StorePickHeaderData(
        val storePickData: List<StoreShopResponse.StorePick>,
        var storeSelected: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonSortData(
        val sortMap: Map<String, Any>,
        var sortSelected: String,
        var gridSelected: Int
    ): ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }


    data class StorePickMoreData(
        var storeSelected: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    class StoreShopEmptyGoodsData: ModuleData() {
        override fun clone(): ModuleData {
            return StoreShopEmptyGoodsData()
        }

        companion object
    }

    data class CommonGoodsGridData(
        val goodsData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsMultiGridData(
        val goodsListData: List<Goods>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsLinearData(
        val goodsData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonGoodsLargeData(
        val goodsData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopCateNameData(
        val text: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class StoreShopCateTabData(
        val storeShopCateList: List<StoreShopResponse.CategoryGoods>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

}
