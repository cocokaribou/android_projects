package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class PlanDetailResponse(
    @SerializedName("data") val data: Data?
) : BaseResponse() {
    data class Data(
        @SerializedName("goods_info") val goodsInfo: List<GoodsInfo>?,
        @SerializedName("tab_list") val tabList: List<Tab>?,
        @SerializedName("plan_shop_info") val planShopInfo: PlanShopInfo?
    )

    data class GoodsInfo(
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?
    ) {
        var tabTitle = ""
    }

    data class Tab(
        @SerializedName("disp_ctg_nm") val dispCtgNm: String?
    )

    data class PlanShopInfo(
        @SerializedName("banner_info") val bannerInfo: BannerInfo?,
        @SerializedName("share_img_path") val shareImgPath: String?,
        @SerializedName("plan_shop_nm") val planShopNm: String?,
        @SerializedName("share_url") val shareUrl: String?
    )

    data class BannerInfo(
        @SerializedName("html_cont") val htmlCont: String?,
        @SerializedName("link_url") val linkUrl: String?
    )
}