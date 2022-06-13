package com.example.shared_viewmodel.model

import com.google.gson.annotations.SerializedName

data class StoreData(
    @field:SerializedName("stoNo") var stoNo: Int?,
    @field:SerializedName("stoName") var stoName: String?,
    @field:SerializedName("stoScore") var stoScore: Int?,
    @field:SerializedName("stoReviewCount") var stoReviewCount: Int?,
    @field:SerializedName("stoImgUrl") var stoImgUrl: String?,
    @field:SerializedName("reviewList") var reviewList: ArrayList<ReviewList>?,
    @field:SerializedName("cateList") var cateList: ArrayList<CategoryData>?
) {
    constructor(stoNo: Int, stoName: String?) : this(
        stoNo = stoNo,
        stoName = stoName,
        stoScore = 0,
        stoReviewCount = 0,
        stoImgUrl = "",
        reviewList = null,
        cateList = null
    )

    data class ReviewList(
        @field:SerializedName("reviewNo") var reviewNo: Int?,
        @field:SerializedName("reviewScore") var reviewScore: Int?,
        @field:SerializedName("reviewContents") var reviewContents: String?,
        @field:SerializedName("mbrNo") var mbrNo: Int?,
        @field:SerializedName("mbrId") var mbrId: String?,
        @field:SerializedName("mbrName") var mbrName: String?,
        @field:SerializedName("mbrProfileImg") var mbrProfileImg: String?
    )

    data class CategoryData(
        @field:SerializedName("cateNo") var cateNo: Int?,
        @field:SerializedName("cateName") var cateName: String?,
    )
}