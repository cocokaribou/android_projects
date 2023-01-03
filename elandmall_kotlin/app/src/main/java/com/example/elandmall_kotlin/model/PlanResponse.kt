package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class PlanResponse(
    @SerializedName("data") val data: Data?,
) : BaseResponse() {
    data class Data(
        @SerializedName("ctg_list") val ctgList: List<Ctg>?,
        @SerializedName("paging_info") val pagingInfo: PagingInfo?,
        @SerializedName("plan_list") val planList: List<Plan>?
    )

    data class Ctg(
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo: String?
    )

    data class PagingInfo(
        @SerializedName("page_idx") val pageIdx: Int?,
        @SerializedName("rows_per_page") val rowsPerPage: Int?,
        @SerializedName("total_count") val totalCount: Int?
    )
}