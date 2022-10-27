package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class FooterTabResponse(
    @SerializedName("data") val data: FooterTabData?
) : BaseResponse() {
    data class FooterTabData(
        @SerializedName("foot_menu") val footMenu: FootMenu,
    )

    data class FootMenu(
        @SerializedName("hosting_service_nm") val hostingServiceNm: String?,
        @SerializedName("person_policy_ga") val personPolicyGa: String?,
        @SerializedName("guide_url_ga") val guideUrlGa: String?,
        @SerializedName("hosting_service") val hostingService: String?,
        @SerializedName("seller_caution") val sellerCaution: String?,
        @SerializedName("corporate_number") val corporateNumber: String?,
        @SerializedName("order_report") val orderReport: String?,
        @SerializedName("person_policy") val personPolicy: String?,
        @SerializedName("isms_scope_nm") val ismsScopeNm: String?,
        @SerializedName("company") val company: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("guide") val guide: String?,
        @SerializedName("order_report_number") val orderReportNumber: String?,
        @SerializedName("email_addr") val emailAddr: String?,
        @SerializedName("compay_addr") val compayAddr: String?,
        @SerializedName("chief") val chief: String?,
        @SerializedName("center") val center: String?,
        @SerializedName("foot_directYn_url") val footDirectYnUrl: String?,
        @SerializedName("isms_scope") val ismsScope: String?,
        @SerializedName("isms_expiry") val ismsExpiry: String?,
        @SerializedName("guide_url") val guideUrl: String?,
        @SerializedName("corporate_confirm") val corporateConfirm: String?,
        @SerializedName("chief_nm") val chiefNm: String?,
        @SerializedName("isms_expiry_date") val ismsExpiryDate: String?,
        @SerializedName("person_report_chief") val personReportChief: String?,
        @SerializedName("person_policy_url") val personPolicyUrl: String?,
        @SerializedName("foot_directYn") val footDirectYn: String?,
        @SerializedName("corporate") val corporate: String?,
        @SerializedName("center_tel") val centerTel: String?,
        @SerializedName("person_report") val personReport: String?,
        @SerializedName("corporate_confirm_url") val corporateConfirmUrl: String?,
        @SerializedName("isms_link_url") val ismsLinkUrl: String?,
        @SerializedName("isms_image_url") val ismsImageUrl: String?,
        @SerializedName("foot_menu_list") val footMenuList: List<FootMenuItem>?
    ) {
        data class FootMenuItem(
            @SerializedName("menu_link") val menuLink: String?,
            @SerializedName("menu_lank") val menuLank: Int?,
            @SerializedName("menu_nm") val menuNm: String?
        )
    }
}