package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class SearchPopNotiResponse(
    @SerializedName("data") val data: SearchPopNotiData?,
) : BaseResponse() {
    data class SearchPopNotiData(
        @SerializedName("noti_no") val notiNo: String?,
        @SerializedName("pop_noti_yn") val popNotiYn: String?,
        @SerializedName("noti_cont") val notiCont: String?,
        @SerializedName("end_time") val endTime: String?,
        @SerializedName("cookie_type") val cookieType: String?,
        @SerializedName("noti_title") val notiTitle: String?
    )
}