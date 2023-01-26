package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

class SearchModule(
    val type: SearchModuleType,
    var data: Any? = null
)

enum class SearchModuleType {
    POPULAR_RANKING,
    POPULAR_PLAN_SHOP,
    RECENTLY_SEARCHED,
    RECENTLY_VIEWED,
    BRAND_TOP10,
    BRAND_ALPHABETS,
    BRAND_LIST_TITLE,
    BRAND_LIST
}

// search_popular.json
data class SearchPopularResponse(
    @SerializedName("result") private val result: List<List<String>?>?
) {
    val validData: List<PopularItem>?
        get() = result?.map {
            PopularItem(brandNm = it?.get(0) ?: "", rankDiff = it?.get(1) ?: "0")
        }?.toList()

    data class PopularItem(
        val brandNm: String,
        val rankDiff: String
    )
}

// search_brand.json
data class SearchBrandResponse(
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

    val validData: List<Planshop>?
        get() = result?.get(0)?.planshop

    data class Planshop(
        @SerializedName("banner_img_path") private val bannerImgPath: String?,
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo: String?
    ) {
        val bannerImgUrl
            get() = "http://www.elandrs.com/upload$bannerImgPath"
    }
}

// search_brand_keyword.json
data class SearchBrandKeyword(
    @SerializedName("data") private val data: List<Data>?,
) {
    data class Data(
        @SerializedName("nav_brand_keyword") val navBrandKeyword: NavBrandKeyword?
    )
    val validData: NavBrandKeyword?
        get() = data?.get(0)?.navBrandKeyword

    data class NavBrandKeyword(
        @SerializedName("nav_brand_keyword_list_kor") val navBrandKeywordListKor: List<NavBrandKeywordItem>?,
        @SerializedName("nav_brand_keyword_list_eng") val navBrandKeywordListEng: List<NavBrandKeywordItem>?
    ) {
        val korList: List<String>
            get() {
                val list = navBrandKeywordListKor?.map { it.navBrandKeywordTitle ?: "" }?.toMutableList() ?: mutableListOf()
                val count = list.size

                val diff = if (count % 6 != 0) 6 - count % 6 else 6
                (0 until diff).forEach { _ -> list.add("") }
                return list
            }

        val engList: List<String>
            get() {
                val list = navBrandKeywordListEng?.map { it.navBrandKeywordTitle ?: "" }?.toMutableList() ?: mutableListOf()
                val count = list.size

                val diff = if (count % 6 != 0) 6 - count % 6 else 6
                (0 until diff).forEach { _ -> list.add("") }
                return list
            }
    }


    data class NavBrandKeywordItem(
        @SerializedName("nav_brand_keyword_count") val navBrandKeywordCount: Int?,
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?
    )
}

data class ModuleBrandData(
    var isKorean: Boolean,
    var savedIndex: Int = 0,
    var data: List<String>,
    var click: ((Boolean, Int) -> Unit)? = null
)

// search_brand_keyword_list.json
data class SearchBrandKeywordList(
    @SerializedName("data") private val data: List<List<Data>?>?,
) {
    val validData: Data?
        get() = data?.get(0)?.get(0)

    data class Data(
        @SerializedName("nav_brand_keyword_title") val navBrandKeywordTitle: String?,
        @SerializedName("nav_brand_keyword_list") private val navBrandKeywordList: List<NavBrandKeyword?>?
    ) {
        val keywordList: List<String>?
            get() = navBrandKeywordList?.map { it?.brandNm ?: "" }

        data class NavBrandKeyword(
            @SerializedName("brand_nm") val brandNm: String?
        )
    }

}