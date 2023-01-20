package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class SearchResultBrandResponse(
    @SerializedName("result") val result: List<Result>?,
) : BaseResponse() {
    data class Result(
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("keyword") val keyword: String?,
        @SerializedName("brand_url") val brandUrl: String?
    )
}

data class SearchResultTop10Response(
    @SerializedName("result") private val result: List<List<String>?>?
) {
    val validData: List<String>?
        get() {
            return result?.map { it?.get(0) ?: "" }
        }
}

data class SearchResultPlanResponse(
    @SerializedName("result") private val result: List<Result>?
) {
    val validData: List<PlanShop>?
        get() = result?.get(0)?.planshop

    data class Result(
        @SerializedName("planshop") val planshop: List<PlanShop>?
    )

    data class PlanShop(
        @SerializedName("banner_img_path") val bannerImgPath: String?,
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo: String?
    )
}
