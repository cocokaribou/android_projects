package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
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
        @SerializedName("home_eyou") val homeEyou: HomeEyou?,
        @SerializedName("home_categoryIcon_list") val homeCategoryIconList: List<HomeCategoryIcon>?,
        @SerializedName("home_brand_list") val homeBrandList: List<HomeBrand>?,
        @SerializedName("home_lucky_deal") val homeLuckyDeal: List<HomeLuckyDeal>?,
        @SerializedName("home_oprice_list") val homeOpriceList: List<HomeOprice>?,
        @SerializedName("home_nc_live") val homeNcLive: HomeNcLive?,
        @SerializedName("home_today_market") val homeTodayMarket: HomeTodayMarket?,
        @SerializedName("home_mainbanner_list") val homeMainbannerList: List<HomeMainbanner>?,
        @SerializedName("home_timesale") val homeTimesale: List<HomeTimesale>?,
        @SerializedName("home_banner_bottom") val homeBannerBottom: HomeBannerBottom?,
        @SerializedName("home_imgbanner") val homeImgbanner: HomeImgbanner?,
        @SerializedName("home_subbanner_list") val homeSubbannerList: List<Any>?,
        @SerializedName("home_banner_top") val homeBannerTop: Banner?,
        @SerializedName("home_shoppingvj") val homeShoppingvj: List<Any>?,
        @SerializedName("home_banner_top_list") val homeBannerTopList: List<Banner>?,
        @SerializedName("home_offers") val homeOffers: HomeOffers?,
        @SerializedName("home_md") val homeMd: HomeMd?,
        @SerializedName("home_week_goods") val homeWeekGoods: List<Any>?,
        @SerializedName("home_offline_shop") val homeOfflineShop: HomeOfflineShop?
    )

    data class HomeCategoryIcon(
        @SerializedName("ga_action") val gaAction: String?,
        @SerializedName("tr_yn") val trYn: String?,
        @SerializedName("shop_copy_nm") val shopCopyNm: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("rel_divi_cd") val relDiviCd: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("move_cont_no") val moveContNo: String?,
        @SerializedName("rel_no") val relNo: String?,
        @SerializedName("conts_dist_no") val contsDistNo: String?,
        @SerializedName("rel_cont_nm") val relContNm: String?,
        @SerializedName("categoryNo") val categoryNo: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("sale_area_no") val saleAreaNo: String?,
        @SerializedName("ga_category") val gaCategory: String?,
        @SerializedName("conts_divi_cd") val contsDiviCd: String?,
        @SerializedName("ga_label") val gaLabel: String?
    )

    data class HomeBrand(
        @SerializedName("ga_action") val gaAction: String?,
        @SerializedName("tr_yn") val trYn: String?,
        @SerializedName("shop_copy_nm") val shopCopyNm: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("rel_divi_cd") val relDiviCd: String?,
        @SerializedName("brand_name") val brandName: String?,
        @SerializedName("move_cont_no") val moveContNo: String?,
        @SerializedName("rel_no") val relNo: String?,
        @SerializedName("conts_dist_no") val contsDistNo: String?,
        @SerializedName("rel_cont_nm") val relContNm: String?,
        @SerializedName("eland_brand_yn") val elandBrandYn: String?,
        @SerializedName("Conts_form_cd") val contsFormCd: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("staff_yn") val staffYn: String?,
        @SerializedName("sale_area_no") val saleAreaNo: String?,
        @SerializedName("disp_seq") val dispSeq: Int?,
        @SerializedName("ga_category") val gaCategory: String?,
        @SerializedName("conts_divi_cd") val contsDiviCd: String?,
        @SerializedName("ga_label") val gaLabel: String?
    )

    data class HomeLuckyDeal(
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?
    ) {
        data class Goods(
            @SerializedName("flag_img_path") val flagImgPath: String?,
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("goods_nm") val goodsNm: String?,
            @SerializedName("wish_zzim_Yn") val wishZzimYn: String?,
            @SerializedName("flag_yn") val flagYn: String?,
            @SerializedName("goods_no") val goodsNo: String?,
            @SerializedName("brand_nm") val brandNm: String?,
            @SerializedName("icon_view") val iconView: String?,
            @SerializedName("cart_grp_cd") val cartGrpCd: String?,
            @SerializedName("conts_dist_no") val contsDistNo: String?,
            @SerializedName("cust_sale_price") val custSalePrice: Int?,
            @SerializedName("sale_qty") val saleQty: Int?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("wish_cart_Yn") val wishCartYn: String?,
            @SerializedName("dlp_category") val dlpCategory: String?,
            @SerializedName("conts_divi_cd") val contsDiviCd: String?,
            @SerializedName("ga_label") val gaLabel: String?,
            @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
            @SerializedName("no_image_url") val noImageUrl: String?,
            @SerializedName("tr_yn") val trYn: String?,
            @SerializedName("sale_rate") val saleRate: Int?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
            @SerializedName("rel_divi_cd") val relDiviCd: String?,
            @SerializedName("sale_price") val salePrice: Int?,
            @SerializedName("rel_no") val relNo: String?,
            @SerializedName("disp_ctg_no") val dispCtgNo: String?,
            @SerializedName("vir_vend_no") val virVendNo: String?,
            @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
            @SerializedName("dlp_list") val dlpList: String?,
            @SerializedName("market_price") val marketPrice: Int?,
            @SerializedName("sale_area_no") val saleAreaNo: String?,
            @SerializedName("ga_category") val gaCategory: String?
        )
    }

    data class HomeTodayMarket(
        @SerializedName("home_today_list") val homeTodayList: List<Any>?,
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?
    )

    data class HomeMainbanner(
        @SerializedName("ga_action") val gaAction: String?,
        @SerializedName("tr_yn") val trYn: String?,
        @SerializedName("shop_copy_nm") val shopCopyNm: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("rel_divi_cd") val relDiviCd: String?,
        @SerializedName("move_cont_no") val moveContNo: String?,
        @SerializedName("rel_no") val relNo: String?,
        @SerializedName("conts_dist_no") val contsDistNo: String?,
        @SerializedName("rel_cont_nm") val relContNm: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("sale_area_no") val saleAreaNo: String?,
        @SerializedName("ga_category") val gaCategory: String?,
        @SerializedName("conts_divi_cd") val contsDiviCd: String?,
        @SerializedName("ga_label") val gaLabel: String?
    )

    data class HomeOfflineShop(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("home_offline_shop_list") val homeOfflineShopList: List<HomeOfflineShopItem>?,
        @SerializedName("title") val title: String?,
        @SerializedName("home_offline_shop_banner") val homeOfflineShopBanner: List<HomeOfflineShopBanner>?
    ) {
        data class HomeOfflineShopItem(
            @SerializedName("flag_img_path") val flagImgPath: String?,
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("goods_nm") val goodsNm: String?,
            @SerializedName("wish_zzim_Yn") val wishZzimYn: String?,
            @SerializedName("flag_yn") val flagYn: String?,
            @SerializedName("goods_no") val goodsNo: String?,
            @SerializedName("brand_nm") val brandNm: String?,
            @SerializedName("cart_grp_cd") val cartGrpCd: String?,
            @SerializedName("conts_dist_no") val contsDistNo: String?,
            @SerializedName("cust_sale_price") val custSalePrice: Int?,
            @SerializedName("lot_deli_yn") val lotDeliYn: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("wish_cart_Yn") val wishCartYn: String?,
            @SerializedName("dlp_category") val dlpCategory: String?,
            @SerializedName("conts_divi_cd") val contsDiviCd: String?,
            @SerializedName("ga_label") val gaLabel: String?,
            @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
            @SerializedName("no_image_url") val noImageUrl: String?,
            @SerializedName("tr_yn") val trYn: String?,
            @SerializedName("sale_rate") val saleRate: Int?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
            @SerializedName("rel_divi_cd") val relDiviCd: String?,
            @SerializedName("sale_price") val salePrice: Int?,
            @SerializedName("rel_no") val relNo: String?,
            @SerializedName("disp_ctg_no") val dispCtgNo: String?,
            @SerializedName("vir_vend_no") val virVendNo: String?,
            @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
            @SerializedName("dlp_list") val dlpList: String?,
            @SerializedName("market_price") val marketPrice: Int?,
            @SerializedName("sale_area_no") val saleAreaNo: String?,
            @SerializedName("ga_category") val gaCategory: String?
        )

        data class HomeOfflineShopBanner(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("tr_yn") val trYn: String?,
            @SerializedName("shop_copy_nm") val shopCopyNm: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("rel_divi_cd") val relDiviCd: String?,
            @SerializedName("move_cont_no") val moveContNo: String?,
            @SerializedName("rel_no") val relNo: String?,
            @SerializedName("conts_dist_no") val contsDistNo: String?,
            @SerializedName("rel_cont_nm") val relContNm: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("sale_area_no") val saleAreaNo: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("conts_divi_cd") val contsDiviCd: String?,
            @SerializedName("ga_label") val gaLabel: String?
        )
    }

    data class HomeTimesale(
        @SerializedName("goods_list") val goodsList: List<GoodsX>?,
        @SerializedName("title") val title: String?
    ) {
        data class GoodsX(
            @SerializedName("flag_img_path") val flagImgPath: String?,
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("goods_nm") val goodsNm: String?,
            @SerializedName("wish_zzim_Yn") val wishZzimYn: String?,
            @SerializedName("flag_yn") val flagYn: String?,
            @SerializedName("goods_no") val goodsNo: String?,
            @SerializedName("brand_nm") val brandNm: String?,
            @SerializedName("icon_view") val iconView: String?,
            @SerializedName("title") val title: String?,
            @SerializedName("cart_grp_cd") val cartGrpCd: String?,
            @SerializedName("conts_dist_no") val contsDistNo: String?,
            @SerializedName("cust_sale_price") val custSalePrice: Int?,
            @SerializedName("sale_qty") val saleQty: Int?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("wish_cart_Yn") val wishCartYn: String?,
            @SerializedName("dlp_category") val dlpCategory: String?,
            @SerializedName("conts_divi_cd") val contsDiviCd: String?,
            @SerializedName("ga_label") val gaLabel: String?,
            @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
            @SerializedName("no_image_url") val noImageUrl: String?,
            @SerializedName("tr_yn") val trYn: String?,
            @SerializedName("sale_rate") val saleRate: Int?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
            @SerializedName("rel_divi_cd") val relDiviCd: String?,
            @SerializedName("sale_price") val salePrice: Int?,
            @SerializedName("rel_no") val relNo: String?,
            @SerializedName("disp_ctg_no") val dispCtgNo: String?,
            @SerializedName("vir_vend_no") val virVendNo: String?,
            @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
            @SerializedName("dlp_list") val dlpList: String?,
            @SerializedName("market_price") val marketPrice: Int?,
            @SerializedName("sale_area_no") val saleAreaNo: String?,
            @SerializedName("time") val time: String?,
            @SerializedName("ga_category") val gaCategory: String?
        )
    }

    data class HomeMd(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("home_md_cat_list") val homeMdCatList: List<HomeMdCat>?,
        @SerializedName("title") val title: String?
    ) {
        data class HomeMdCat(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("home_md_goods") val homeMdGoods: List<HomeMdGood>?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("menu_title") val menuTitle: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("ga_label") val gaLabel: String?
        ) {
            data class HomeMdGood(
                @SerializedName("flag_img_path") val flagImgPath: String?,
                @SerializedName("ga_action") val gaAction: String?,
                @SerializedName("goods_nm") val goodsNm: String?,
                @SerializedName("flag_yn") val flagYn: String?,
                @SerializedName("goods_no") val goodsNo: String?,
                @SerializedName("goods_star_point") val goodsStarPoint: Int?,
                @SerializedName("brand_nm") val brandNm: String?,
                @SerializedName("cart_grp_cd") val cartGrpCd: String?,
                @SerializedName("conts_dist_no") val contsDistNo: String?,
                @SerializedName("cust_sale_price") val custSalePrice: Int?,
                @SerializedName("lot_deli_yn") val lotDeliYn: String?,
                @SerializedName("link_url") val linkUrl: String?,
                @SerializedName("dlp_category") val dlpCategory: String?,
                @SerializedName("conts_divi_cd") val contsDiviCd: String?,
                @SerializedName("ga_label") val gaLabel: String?,
                @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
                @SerializedName("no_image_url") val noImageUrl: String?,
                @SerializedName("tr_yn") val trYn: String?,
                @SerializedName("sale_rate") val saleRate: Int?,
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
                @SerializedName("rel_divi_cd") val relDiviCd: String?,
                @SerializedName("sale_price") val salePrice: Int?,
                @SerializedName("rel_no") val relNo: String?,
                @SerializedName("disp_ctg_no") val dispCtgNo: String?,
                @SerializedName("vir_vend_no") val virVendNo: String?,
                @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
                @SerializedName("dlp_list") val dlpList: String?,
                @SerializedName("market_price") val marketPrice: Int?,
                @SerializedName("sale_area_no") val saleAreaNo: String?,
                @SerializedName("goods_comment_count") val goodsCommentCount: Int?,
                @SerializedName("ga_category") val gaCategory: String?
            )
        }
    }

    data class HomeOffers(
        @SerializedName("subtitle") val subtitle: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("home_offers_list") val homeOffersList: List<HomeOffersItem>?
    ) {
        data class HomeOffersItem(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("tr_yn") val trYn: String?,
            @SerializedName("shop_copy_nm") val shopCopyNm: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("rel_divi_cd") val relDiviCd: String?,
            @SerializedName("move_cont_no") val moveContNo: String?,
            @SerializedName("rel_no") val relNo: String?,
            @SerializedName("conts_dist_no") val contsDistNo: String?,
            @SerializedName("rel_cont_nm") val relContNm: String?,
            @SerializedName("goods_list") val goodsList: List<Goods>?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("sale_area_no") val saleAreaNo: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("conts_divi_cd") val contsDiviCd: String?,
            @SerializedName("ga_label") val gaLabel: String?
        ) {
            data class Goods(
                @SerializedName("flag_img_path") val flagImgPath: String?,
                @SerializedName("ga_action") val gaAction: String?,
                @SerializedName("goods_nm") val goodsNm: String?,
                @SerializedName("wish_zzim_Yn") val wishZzimYn: String?,
                @SerializedName("flag_yn") val flagYn: String?,
                @SerializedName("goods_no") val goodsNo: String?,
                @SerializedName("brand_nm") val brandNm: String?,
                @SerializedName("cart_grp_cd") val cartGrpCd: String?,
                @SerializedName("conts_dist_no") val contsDistNo: String?,
                @SerializedName("cust_sale_price") val custSalePrice: Int?,
                @SerializedName("link_url") val linkUrl: String?,
                @SerializedName("wish_cart_Yn") val wishCartYn: String?,
                @SerializedName("dlp_category") val dlpCategory: String?,
                @SerializedName("conts_divi_cd") val contsDiviCd: String?,
                @SerializedName("ga_label") val gaLabel: String?,
                @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
                @SerializedName("no_image_url") val noImageUrl: String?,
                @SerializedName("tr_yn") val trYn: String?,
                @SerializedName("sale_rate") val saleRate: Int?,
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
                @SerializedName("rel_divi_cd") val relDiviCd: String?,
                @SerializedName("sale_price") val salePrice: Int?,
                @SerializedName("rel_no") val relNo: String?,
                @SerializedName("disp_ctg_no") val dispCtgNo: String?,
                @SerializedName("vir_vend_no") val virVendNo: String?,
                @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
                @SerializedName("dlp_list") val dlpList: String?,
                @SerializedName("market_price") val marketPrice: Int?,
                @SerializedName("sale_area_no") val saleAreaNo: String?,
                @SerializedName("ga_category") val gaCategory: String?,
                @SerializedName("lot_deli_yn") val lotDeliYn: String?
            )
        }
    }


}