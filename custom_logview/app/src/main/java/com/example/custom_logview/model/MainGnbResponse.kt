package com.example.custom_logview.model

import com.google.gson.annotations.SerializedName

data class MainGnbResponse(
    @SerializedName("data") val data: MainGnbData?,
) {
    data class MainGnbData(
        @SerializedName("header_icon_list") val headerIconList: List<Header>?,
        @SerializedName("search_ad") val searchAd: SearchAd?,
    )

    data class Header(
        @SerializedName("ga_action") val gaAction: String?,
        @SerializedName("webview") val webview: String?,
        @SerializedName("menu_subtitle") val menuSubtitle: String?,
        @SerializedName("menu_cd") val menuCd: String?,
        @SerializedName("api_url") val apiUrl: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("menu_lank") val menuRank: Int?,
        @SerializedName("menu_nm") val menuName: String?,
        @SerializedName("new_tag_yn") val newTagYn: String?,
        @SerializedName("ga_category") val gaCategory: String?,
        @SerializedName("ga_label") val gaLabel: String?,
    )

    data class SearchAd(
        @SerializedName("tr_yn") val tryYn: String?,
        @SerializedName("ga_action") val gaAction: String?,
        @SerializedName("rel_divi_cd") val relDiviCd: String?,
        @SerializedName("sale_area_no") val saleAreaNo: String?,
        @SerializedName("searchAdLnkUrl") val searchAdLnkUrl: String?,
        @SerializedName("searchAd") val searchAd: String?,
        @SerializedName("ga_category") val gaCategory: String?,
        @SerializedName("conts_divi_cd") val contsDiviCd: String?,
        @SerializedName("rel_no") val relNo: String?,
        @SerializedName("conts_dist_no") val contsDistNo: String?,
        @SerializedName("ga_label") val gaLabel: String?,

        )
}
