package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SplashListResponse(
    @SerializedName("data") val data: SplashData?
) : BaseResponse() {
    data class SplashData(
        @SerializedName("splash_image") val splashImage: List<SplashImage>?
    )

    data class SplashImage(
        @SerializedName("rank") val rank: String?,
        @SerializedName("linkUrl") val linkUrl: String?,
    )
}
