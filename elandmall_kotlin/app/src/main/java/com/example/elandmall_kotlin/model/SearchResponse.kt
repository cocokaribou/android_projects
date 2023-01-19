package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class PopularItem(
    val brandNm: String,
    val rankDiff: String
)

// search_popular.json
data class SearchPopularResponse(
    @SerializedName("result") private val result: List<List<String>?>?
) {
    val validData: List<PopularItem>?
        get() = result?.map {
            PopularItem(brandNm = it?.get(0) ?: "", rankDiff = it?.get(1) ?: "")
        }?.toList()
}

// search_brand.json
data class SearchPopularBrandResponse(
    @SerializedName("result") private val result: List<List<String>?>?
) {
    val validData: List<String>?
        get() = result?.map {
            it?.get(0) ?: ""
        }
}

// search_planshop.json
data class SearchPlanShopResponse(
    @SerializedName("result") private val result: List<Result>?
) {
    data class Result(
        @SerializedName("planshop") val planshop: List<Planshop>?
    )

    val validData: Planshop?
        get() = result?.get(0)?.planshop?.get(0)

    data class Planshop(
        @SerializedName("banner_img_path") val bannerImgPath: String?,
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo: String?
    )
}

// search_brand_keyword.json
data class SearchBrandKeyword(
    @SerializedName("data") private val data: List<Data>?,
) {
    val validData: Data?
        get() = data?.get(0)

    data class Data(
        @SerializedName("nav_brand_keyword") val navBrandKeyword: NavBrandKeyword?
    )

    data class NavBrandKeyword(
        @SerializedName("nav_brand_keyword_list_kor") val navBrandKeywordListKor: List<NavBrandKeywordKor?>?,
        @SerializedName("nav_brand_keyword_list_eng") val navBrandKeywordListEng: List<NavBrandKeywordEng?>?
    )

    data class NavBrandKeywordKor(
        @SerializedName("nav_brand_keyword_count") val navBrandKeywordCount: Int?,
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?
    )

    data class NavBrandKeywordEng(
        @SerializedName("nav_brand_keyword_count") val navBrandKeywordCount: Int?,
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?
    )
}

// search_brand_keyword_list.json
data class SearchBrandKeywordList(
    @SerializedName("data") private val data: List<List<Data>?>?,
) {
    val validData: Data?
        get() = data?.get(0)?.get(0)

    data class Data(
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?,
        @SerializedName("nav_brand_keyword_list") val navBrandKeywordList: List<NavBrandKeyword?>?
    )

    data class NavBrandKeyword(
        @SerializedName("brand_nm") val brandNm: String?
    )
}