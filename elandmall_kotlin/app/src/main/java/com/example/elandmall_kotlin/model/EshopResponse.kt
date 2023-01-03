package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class EshopResponse(
    @SerializedName("data") val data: Data?,
): BaseResponse() {
    data class Data(
        @SerializedName("eshop_sub_banner_list") val eshopSubBannerList: List<Banner>?,
        @SerializedName("eshop_now_issue") val eshopNowIssue: EshopNowIssue?,
        @SerializedName("eshop_banner_list2") val eshopBannerList2: List<Banner>?,
        @SerializedName("eshop_banner_list1") val eshopBannerList1: List<Banner>?,
        @SerializedName("ctg_list") val ctgList: List<Ctg>?,
        @SerializedName("eshop_main_promotion_list") val eshopMainPromotionList: List<Banner>?,
        @SerializedName("ctg_navi") val ctgNavi: List<Ctg>?,
        @SerializedName("eshop_now_arrival") val eshopNowArrival: EshopNowArrival?
    ) {
        data class EshopNowIssue(
            @SerializedName("title") val title: String?,
            @SerializedName("group") val group: List<Group>?
        ) {
            data class Group(
                @SerializedName("goods_list") val goodsList: List<Goods>?,
                @SerializedName("tab_title") val tabTitle: String?,
                @SerializedName("planshop_list") val planshopList: PlanshopList?
            ) {
                data class PlanshopList(
                    @SerializedName("image_url") val imageUrl: String?,
                    @SerializedName("link_url") val linkUrl: String?
                )
            }
        }

        data class Ctg(
            @SerializedName("ctg_no") val ctgNo: String?,
            @SerializedName("ctg_nm") val ctgNm: String?
        )

        data class EshopNowArrival(
            @SerializedName("title") val title: String?,
            @SerializedName("group") val group: List<Group>?
        ) {
            data class Group(
                @SerializedName("goods_list") val goodsList: List<Goods>?,
                @SerializedName("category") val category: String?
            )
        }
    }
}