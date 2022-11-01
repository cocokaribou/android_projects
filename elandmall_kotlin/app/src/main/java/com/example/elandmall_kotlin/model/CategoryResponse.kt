package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data") val data: CategoryData?,
) : BaseResponse() {
    data class CategoryData(
        @SerializedName("login_info") val loginInfo: LoginInfo?,
        @SerializedName("nav_cat_servicemenu_list") val navCatServicemenuList: List<NavCatServicemenu>?,
        @SerializedName("nav_cat_top_menu_list") val navCatTopMenuList: List<NavCatTopMenu>?,
        @SerializedName("nav_cat_shop_list") val navCatShopList: List<NavCatShop>?,
        @SerializedName("nav_cat_lately_goods_list") val navCatLatelyGoodsList: List<Any>?,
        @SerializedName("nav_cat_category_list") val navCatCategoryList: List<NavCatCategory>?,
        @SerializedName("nav_cat_brand_list") val navCatBrandList: List<NavCatBrand>?
    ) {
        data class LoginInfo(
            @SerializedName("autoLogin") val autoLogin: String?,
            @SerializedName("pop_type") val popType: String?,
            @SerializedName("birth_day") val birthDay: String?,
            @SerializedName("wedd_cele_day") val weddCeleDay: String?,
            @SerializedName("user_id") val userId: String?,
            @SerializedName("mbr_grade_nm") val mbrGradeNm: String?,
            @SerializedName("pop_confirm_url") val popConfirmUrl: String?,
            @SerializedName("pop_title") val popTitle: String?,
            @SerializedName("staff_yn") val staffYn: String?,
            @SerializedName("pop_url") val popUrl: String?,
            @SerializedName("mbr_nm") val mbrNm: String?,
            @SerializedName("cust_id") val custId: String?,
            @SerializedName("pop_cancel_url") val popCancelUrl: String?
        )

        data class NavCatBrand(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("brand_nm") val brandNm: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("ga_label") val gaLabel: String?
        )

        data class NavCatCategory(
            @SerializedName("nav_cat_category_menu_list") val navCatCategoryMenuList: List<NavCatCategoryMenu>?,
            @SerializedName("nav_cat_category_menu") val navCatCategoryMenu: String?
        ) {
            data class NavCatCategoryMenu(
                @SerializedName("ga_action") val gaAction: String?,
                @SerializedName("image_url") val imageUrl: String?,
                @SerializedName("menu_title") val menuTitle: String?,
                @SerializedName("link_url") val linkUrl: String?,
                @SerializedName("ga_category") val gaCategory: String?,
                @SerializedName("ga_label") val gaLabel: String?
            )
        }

        data class NavCatServicemenu(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("service_menu") val serviceMenu: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("ga_label") val gaLabel: String?
        )

        data class NavCatShop(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("image_url") val imageUrl: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("staff_yn") val staffYn: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("shop_nm") val shopNm: String?,
            @SerializedName("ga_label") val gaLabel: String?
        )

        data class NavCatTopMenu(
            @SerializedName("ga_action") val gaAction: String?,
            @SerializedName("menu_info") val menuInfo: String?,
            @SerializedName("link_url") val linkUrl: String?,
            @SerializedName("menu_nm") val menuNm: String?,
            @SerializedName("ga_category") val gaCategory: String?,
            @SerializedName("ga_label") val gaLabel: String?,
            @SerializedName("cart_cnt") val cartCnt: Int?,
            @SerializedName("cupn_cnt") val cupnCnt: Int?
        )
    }
}