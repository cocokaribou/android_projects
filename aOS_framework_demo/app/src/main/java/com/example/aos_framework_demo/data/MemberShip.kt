package com.example.aos_framework_demo.data

import com.google.gson.annotations.SerializedName

data class MemberShip (
    @field:SerializedName("memberInfo") var memberInfo: MemberInfo,
    @field:SerializedName("bannerInfo") var bannerInfo: BannerInfo
)
{
    data class MemberInfo(
        @field:SerializedName("reviewCnt") var reviewCnt: Int,
        @field:SerializedName("reviewPnt") var reviewPnt: Int,
        @field:SerializedName("stampYn") var stampYn: String,
        @field:SerializedName("stampMaxStage") var stampMaxStage: Int,
        @field:SerializedName("stampRealSaveQty") var stampRealSaveQty: Int,
        @field:SerializedName("cpnCnt") var cpnCnt: Int,
    )
    data class BannerInfo(
        @field:SerializedName("bannerImgPath") var bannerImgPath: String,
        @field:SerializedName("urlMove") var urlMove: String
    )
}