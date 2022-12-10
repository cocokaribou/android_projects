package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class StorePickResponse(
    @SerializedName("data") val data: Data?,
    @SerializedName("keyword") val keyword: String?,
):BaseResponse() {
    data class Data(
        @SerializedName("relate_kwds") val relateKwds: List<Any>?,
        @SerializedName("keyword_result") val keywordResult: KeywordResult?
    ) {
        data class KeywordResult(
            @SerializedName("search_goods") val searchGoods: List<Goods>?,
            @SerializedName("total_count") val totalCount: Int?,
            @SerializedName("result_count") val resultCount: Int?
        )
    }
}