package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class EKidsResponse(
    @SerializedName("data") val data: Data?
) : BaseResponse() {
    data class Data(
        @SerializedName("ctg_list") val ctgList: List<Ctg>?,
        @SerializedName("new_arrival") val newArrival: NewArrival?,
        @SerializedName("band_banner") val bandBanner: List<Banner>?,
        @SerializedName("sub_banner") val subBanner: List<Banner>?,
        @SerializedName("weekly_best") val weeklyBest: WeeklyBest?,
        @SerializedName("special_deal") val specialDeal: SpecialDeal?,
        @SerializedName("brand_story") val brandStory: BrandStory?,
        @SerializedName("main_banner") val mainBanner: List<Banner>?
    )

    data class Ctg(
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("link_url") val linkUrl: String?,
        @SerializedName("ctg_nm") val ctgNm: String?
    )

    data class NewArrival(
        @SerializedName("title") val title: String?,
        @SerializedName("group") val group: List<Group>?
    )

    data class WeeklyBest(
        @SerializedName("title") val title: String?,
        @SerializedName("group") val group: List<Group>?
    )

    data class Group(
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("ctg_nm") val ctgNm: String?
    )

    data class SpecialDeal(
        @SerializedName("goods_list") val goodsList: List<Goods>?,
        @SerializedName("title") val title: String?
    )

    data class BrandStory(
        @SerializedName("banner_list") val bannerList: List<Banner>?,
        @SerializedName("title") val title: String?
    )
}