package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class BestResponse(
    @SerializedName("data") val data: Data?,
) : BaseResponse() {
    data class Data(
        @SerializedName("disp_ctg_list") val dispCtgList: List<DispCtg>?,
    )

    data class DispCtg(
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("disp_ctg_show_nm") val dispCtgShowNm: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo: String?,
        @SerializedName("ldisp_ctg_no") val ldispCtgNo: String?,
        @SerializedName("ldisp_ctg_show_nm") val ldispCtgShowNm: String?
    )
}

data class BestTabResponse(
    @SerializedName("data") val data: Data?,
) {
    data class Data(
        @SerializedName("goods_info") val goodsInfo: GoodsInfo?
    )

    data class GoodsInfo(
        @SerializedName("page_idx") val pageIdx: Int?,
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("rows_per_page") val rowsPerPage: Int?,
        @SerializedName("goods_Total_count") val goodsTotalCount: Int?
    )
}
