package com.example.elandmall_kotlin.common

/**
 * global-scope constant variables
 */
object CommonConst {
    const val DEV = "dev-"
    const val QAS = "qas-"
    const val STG = "stg-"

    val mainDomain = "http://m.elandmall.com"

    /**
     * gnb tab code
     */
    const val HOME_MENU_CD = "10"
    const val LUCKYDEAL_MENU_CD = "20"
    const val BEST_MENU_CD = "30"
    const val PLAN_MENU_CD = "40"
    const val OUTLET_MENU_CD = "50"
    const val STORE_MENU_CD = "60"
    const val SHOPPING_VJ_MENU_CD = "70"
    const val EVENT_MENU_CD = "80"
    const val KIMS_MENU_CD = "90"
    const val OVERSEA_MENU_CD = "100"
    const val EKIDS_MENU_CD = "110"
    const val ESHOP_MENU_CD = "120"
    const val PLAN_DETAIL_MENU_CD = "130"
    const val OPRICE_MENU_CD = "140"
    const val COUPON_MENU_CD = "160"

    /**
     * scheme / host
     */
    const val SCHEME_ELANDMALL = "elandmall"
    const val HOST_LANDING = "landing"

    /**
     * intent extra keys
     */
    const val EXTRA_LINK_EVENT = "extra_link_event"
    const val EXTRA_SEARCH_TAB = "extra_search_type"
}

// api result flag
enum class ApiResult {
    SUCCESS,
    FAIL,
    EXCEPTION
}