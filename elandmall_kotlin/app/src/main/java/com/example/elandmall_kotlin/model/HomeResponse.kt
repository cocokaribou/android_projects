package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("data") val data: HomeData?,
) : BaseResponse() {
    /**
     * empty objects from prod api
     */
    class HomeEyou
    class HomeNcLive
    class HomeBannerBottom
    class HomeImgbanner
    class HomeOprice

    data class HomeData(
        // 빌보드 배너
        @SerializedName("home_mainbanner_list") val homeMainbannerList: List<Banner>?,
        // 카테고리 리스트
        @SerializedName("home_categoryIcon_list") val homeCategoryIconList: List<HomeCategoryIcon>?,
        // 상단 배너
        @SerializedName("home_banner_top") val homeBannerTop: Banner?,
        // 상단 띠 배너
        @SerializedName("home_banner_top_list") val homeBannerTopList: List<Banner>?,
        // 서브배너
        @SerializedName("home_subbanner_list") val homeSubbannerList: List<Any>?,
        // 브랜드 NEW
        @SerializedName("home_brand_list") val homeBrandList: List<HomeBrand>?,
        // 쇼핑 VJ
        @SerializedName("home_shoppingvj") val homeShoppingvj: List<Any>?,
        // 이미지 배너
        @SerializedName("home_imgbanner") val homeImgbanner: HomeImgbanner?,
        // NC LIVE
        @SerializedName("home_nc_live") val homeNcLive: HomeNcLive?,
        // 타임세일
        @SerializedName("home_timesale") val homeTimesale: Goods?,
        // eYOU
        @SerializedName("home_eyou") val homeEyou: HomeEyou?,
        // 럭키딜
        @SerializedName("home_lucky_deal") val homeLuckyDeal: HomeLuckyDeal?,
        // 중간 띠 배너
        @SerializedName("home_banner_bottom") val homeBannerBottom: HomeBannerBottom?,
        // 오늘의 장보기
        @SerializedName("home_today_market") val homeTodayMarket: HomeTodayMarket?,
        // 기획전
        @SerializedName("home_offers") val homeSeasonPlan: HomeSeasonPlan?,
        // 오프라이스
        @SerializedName("home_oprice_list") val homeOpriceList: List<HomeOprice>?,
        // (구) 오프라인 쇼핑
        @SerializedName("home_offline_shop") val homeOfflineShop: HomeOfflineShop?,
        // MD 목록
        @SerializedName("home_md") val homeMd: HomeMd?,
    )

    data class HomeCategoryIcon(
        @SerializedName("image_url") private val imgUrl: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("categoryNo") val categoryNo: String?,
        @SerializedName("link_url") val linkUrl: String?,
    ) {
        val imageUrl: String
            get() {
                return if (!imgUrl.isNullOrEmpty() && imgUrl.startsWith("http")) {
                    imgUrl
                } else {
                    "http:$imgUrl"
                }
            }
    }

    data class HomeBrand(
        @SerializedName("image_url") private val imgUrl: String?,
        @SerializedName("brand_name") val brandName: String?,
        @SerializedName("link_url") val linkUrl: String?,
    ) {
        val imageUrl: String
            get() {
                return if (!imgUrl.isNullOrEmpty() && imgUrl.startsWith("http")) {
                    imgUrl
                } else {
                    "http:$imgUrl"
                }
            }
    }

    data class HomeLuckyDeal(
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?
    )

    data class HomeTodayMarket(
        @SerializedName("home_today_list") val homeTodayList: List<Any>?,
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?
    )

    data class HomeOfflineShop(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("home_offline_shop_list") val homeOfflineShopList: List<Goods>?,
        @SerializedName("title") val title: String?,
        @SerializedName("home_offline_shop_banner") val homeOfflineShopBanner: List<Banner>?
    )

    data class HomeMd(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("home_md_cat_list") val homeMdCatList: List<HomeMdCat>?,
        @SerializedName("title") val title: String?
    ) {
        data class HomeMdCat(
            @SerializedName("home_md_goods") val homeMdGoods: List<Goods>?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("menu_title") val menuTitle: String?,
        )
    }

    data class HomeSeasonPlan(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("home_offers_list") val homeSeasonPlanList: List<HomeSeasonPlanItem>?
    ) {
        data class HomeSeasonPlanItem(
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("goods_list") val goodsList: List<Goods>?,
            @SerializedName("link_url") val linkUrl: String?,
        )
    }
}