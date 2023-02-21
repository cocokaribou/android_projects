package com.example.elandmall_kotlin.model


import com.google.gson.annotations.SerializedName

data class StoreShopResponse(
    @SerializedName("data") val data: Data?,
):BaseResponse() {
    data class Data(
        @SerializedName("smartpick_list") val storePickList: MutableList<StorePick>?,
        @SerializedName("banjjak_deli") val banjjakDeli: BanjjakDeli?,
        @SerializedName("store_banner_list") val storeBannerList: List<Any>?,
        @SerializedName("ctg_navi") val ctgNavi: List<CtgNavi>?,
        @SerializedName("recommend_store_list") val recommendStoreList: List<RecommendStore>?,
        @SerializedName("category_goods_list") val categoryGoodsList: List<CategoryGoods>?,
        @SerializedName("my_regular_store_list") val myRegularStoreList: List<MyRegularStore>?,
        @SerializedName("store_mainbanner_list") val storeMainbannerList: List<Banner>?
    )

    class MyRegularStore

    data class StorePick(
        @SerializedName("rel_cont_nm") val relContNm: String?,
        @SerializedName("category_no") var categoryNo: String?,
    )

    data class BanjjakDeli(
        @SerializedName("btn_nm") val btnNm: String?,
        @SerializedName("flag") val flag: String?,
        @SerializedName("btn_url") val btnUrl: String?,
        @SerializedName("img_path") val imgPath: String?,
        @SerializedName("img_link") val imgLink: String?,
        @SerializedName("addr") val addr: String?,
        // when login
        @SerializedName("banjjak_shop") val banjjakShop: BanjjakShop
    )

    data class BanjjakShop(
        @SerializedName("quick_deli_poss_yn") val quickDeliPossYN: String?,
        @SerializedName("vend_info") val vendInfo: String?,
        @SerializedName("store_pagesize") val storePageSize : Int?,
        @SerializedName("banner") val banner: String?,
        @SerializedName("disp_ctg_no") val dispCtgNo : String?
    )

    data class CtgNavi(
        @SerializedName("ctg_no") val ctgNo: String?,
        @SerializedName("ctg_nm") val ctgNm: String?
    )

    data class RecommendStore(
        @SerializedName("store_nm") val storeNm: String?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("link_url") val linkUrl: String?
    ) {
        constructor(link: String) : this("", "", link)
    }

    data class CategoryGoods(
        @SerializedName("goods_list") val goodsList: MutableList<Goods>?,
        @SerializedName("ctg_no") val ctgNo: String?,
        @SerializedName("dactive_img_url") val dactiveImgUrl: String?,
        @SerializedName("ctg_nm") val ctgNm: String?,
        @SerializedName("active_img_url") val activeImgUrl: String?
    )
}