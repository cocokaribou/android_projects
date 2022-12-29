package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class LuckyDealResponse(
    @SerializedName("data") val data: Data?,
) : BaseResponse() {
    data class Data(
        @SerializedName("disp_ctg_list") val dispCtgList: List<DispCtg>?,
        @SerializedName("new_goods_list") val newGoodsList: List<Goods>?,
        @SerializedName("conr_set_no") val conrSetNo: String?,
        @SerializedName("conr_set_cmps_no") val conrSetCmpsNo: String?,
    )

    data class DispCtg(
        @SerializedName("conr_set_no") val conrSetNo: String?,
        @SerializedName("conr_set_cmps_no") val conrSetCmpsNo: String?,
        @SerializedName("ctg_nm") val ctgNm: String?,
        @SerializedName("img") val img: String?
    )
}

data class LuckyDealTabResponse(
    @SerializedName("data") val data: Data?,
) : BaseResponse() {
    data class Data(
        @SerializedName("goods_info") val goodsInfo: GoodsInfo?,
        @SerializedName("banner_list") val bannerList: List<Banner>?
    )

    data class GoodsInfo(
        @SerializedName("page_idx") val pageIdx: String?,
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("rows_per_page") val rowsPerPage: String?,
        @SerializedName("goods_Total_count") val goodsTotalCount: Int?
    )
}