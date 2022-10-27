package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class PreloadResponse(
    @SerializedName("cache_key") val cacheKey: String?,
    @SerializedName("data") val data: PreloadData?,
) : BaseResponse() {
    data class PreloadData(
        @SerializedName("App_version") val appVersion: AppVersion?,
        @SerializedName("hidden") val hidden: List<String>?,
        @SerializedName("srch_api_key") val searchApiKey: String?,
        @SerializedName("app_protocol") val appProtocol: AppProtocol?,
        @SerializedName("header_hidden_domain") val hiddenDomain: List<String>?,
        @SerializedName("url") val url: Url?,
    ) {
        data class AppVersion(
            @SerializedName("app_version") val appVersion: String?,
            @SerializedName("app_webivew") val appWebView: String?,
            @SerializedName("app_update") val appUpdate: String?
        )

        data class AppProtocol(
            @SerializedName("prd_protocol") val prdProtocol: String?,
            @SerializedName("dev_protocol") val devProtocol: String?,
            @SerializedName("qas_protocol") val qasProtocol: String?,
            @SerializedName("stg_protocol") val stgProtocol: String?
        )

        data class Url(
            @SerializedName("delivery") val delivery: String?,
            @SerializedName("logout_url") val logoutUrl: String?,
            @SerializedName("login_url") val loginUrl: String?,
            @SerializedName("question_url") val questionUrl: String?,
            @SerializedName("notice_url") val noticeUrl: String?,
            @SerializedName("lately_url") val latelyUrl: String?,
            @SerializedName("home") val home: String?
        )
    }
}
