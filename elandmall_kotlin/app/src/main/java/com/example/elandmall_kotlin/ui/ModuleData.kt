package com.example.elandmall_kotlin.ui

import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.model.*

sealed class ModuleData {
    abstract fun clone(): ModuleData

    data class CommonMainBannerData(
        val mainBannerData: List<Banner>,
        var isDividerVisible: Boolean = true,
        var isIndicatorVisible: Boolean = true
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

    data class CommonMultiBannerData(
        val multiBannerData: List<Banner>,
        var isDividerVisible: Boolean = true
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class HomeTimeSaleData(
        val homeTimeSaleData: Goods
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

    data class HomeMdRecommendData(
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
        val tabType: TabType,
        val sortMap: Map<String, Any>,
        val isTopPaddingVisible: Boolean,
        var sortSelected: String,
        var gridSelected: Int
    ) : ModuleData() {
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

    class StoreShopEmptyGoodsData : ModuleData() {
        override fun clone(): ModuleData {
            return StoreShopEmptyGoodsData()
        }

        companion object
    }

    data class CommonGoodsGridData(
        val goodsListData: List<Goods>,
        var isDividerVisible: Boolean = true
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

    data class StoreShopCateTitleData(
        val text: String,
        val index: Int
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

    data class CommonCenterTitleData(
        val title: String,
        val titleStyle: String,
        var isDividerVisible: Boolean = true
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class CommonWebViewData(
        val contentHtml: String,
        val contentUrl: String?,
        val shareUrl: String?,
        val shareImgPath: String?
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class PlanDetailTabTitleData(
        val title: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class EKidsCategoryData(
        val eKidsCateList: List<EKidsResponse.Ctg>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class EKidsSpecialGoodsData(
        val goodsData: Goods
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class EKidsBrandData(
        val brandList: List<Banner>
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class EKidsRecommendCategoryData(
        val categoryList: List<String>,
        var cateSelected: Int,
        val viewType: String
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class EKidsExpandableData(
        val viewType: String,
        var isExpanded: Boolean = false
    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }
}
