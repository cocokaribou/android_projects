package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

class LeftMenuModule(
    val type: ModuleType,
    val data: Any? = null
)

enum class ModuleType {
    DIVIDER,
    RECENTLY,
    CATEGORY,
    BRAND,
    SHOP,
    SERVICE_MENU,
    FOOTER
}

data class LeftMenuResponse(
    @SerializedName("data") val data: Data?,
) : BaseResponse() {
    data class Data(
        @SerializedName("login_info") val loginInfo: LoginInfo?,
        @SerializedName("nav_cat_brand_list") val navCatBrandList: List<NavCatBrand?>?,
        @SerializedName("nav_cat_category_list") val navCatCategoryList: List<NavCatCategory?>?,
        @SerializedName("nav_cat_lately_goods_list") val navCatLatelyGoodsList: List<Any?>?,
        @SerializedName("nav_cat_servicemenu_list") val navCatServicemenuList: List<NavCatServicemenu?>?,
        @SerializedName("nav_cat_shop_list") val navCatShopList: List<NavCatShop?>?,
        @SerializedName("nav_cat_top_menu_list") val navCatTopMenuList: List<NavCatTopMenu?>?
    )
    data class LoginInfo(
        @SerializedName("autoLogin") val autoLogin: String?,
        @SerializedName("birth_day") val birthDay: String?,
        @SerializedName("cust_id") val custId: String?,
        @SerializedName("mbr_grade_nm") val mbrGradeNm: String?,
        @SerializedName("mbr_nm") val mbrNm: String?,
        @SerializedName("pop_cancel_url") val popCancelUrl: String?,
        @SerializedName("pop_confirm_url") val popConfirmUrl: String?,
        @SerializedName("pop_title") val popTitle: String?,
        @SerializedName("pop_type") val popType: String?,
        @SerializedName("pop_url") val popUrl: String?,
        @SerializedName("staff_yn") val staffYn: String?,
        @SerializedName("user_id") val userId: String?,
        @SerializedName("wedd_cele_day") val weddCeleDay: String?
    ) {
        val isLogin: Boolean
            get() = !userId.isNullOrEmpty()
    }

    data class NavCatBrand(
        @SerializedName("brand_nm") val brandNm: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("link_url") val linkUrl: String?
    )

    data class NavCatCategory(
        @SerializedName("nav_cat_category_menu") val navCatCategoryMenu: String?,
        @SerializedName("nav_cat_category_menu_list") val navCatCategoryMenuList: List<NavCatCategoryMenu?>?
    )
    data class NavCatCategoryMenu(
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("menu_title") val menuTitle: String?
    )

    data class NavCatServicemenu(
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("service_menu") val serviceMenu: String?
    )

    data class NavCatShop(
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("shop_nm") val shopNm: String?,
        @SerializedName("staff_yn") val staffYn: String?
    )

    data class NavCatTopMenu(
        @SerializedName("cart_cnt") val cartCnt: Int?,
        @SerializedName("cupn_cnt") val cupnCnt: Int?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("menu_info") val menuInfo: String?,
        @SerializedName("menu_nm") val menuNm: String?
    )
}