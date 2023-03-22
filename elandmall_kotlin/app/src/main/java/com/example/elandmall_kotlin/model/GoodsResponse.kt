package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

class GoodsModule(
    val type: GoodsModuleType,
    var data: Any? = null
)

enum class GoodsModuleType {
    GOODS_HEADER,
    GOODS_TOP_IMAGE,
    GOODS_INFO,
    GOODS_TAB,
    GOODS_DETAIL_POPULAR,
    GOODS_DETAIL_RECOM,
    GOODS_DETAIL_SELLER_POPULAR,
    GOODS_DETAIL_SELLER_RECOM,
    GOODS_DETAIL_WEB,
    GOODS_DETAIL_TAG,
    GOODS_REVIEW_EMPTY,
    GOODS_REVIEW_PHOTO,
    GOODS_REVIEW_PREVIEW,
    GOODS_REVIEW_TEXT,
    GOODS_QNA_FORM,
    GOODS_QNA_LIST,
    GOODS_ORDER_INFO,
    GOODS_ORDER_INFO_STORE,
    GOODS_BOTTOM_MARGIN
}

data class GoodsResponse(
    @SerializedName("data") val data: Data?,
):BaseResponse() {
    data class Data(
        @SerializedName("top_image_list") val topImageList: List<TopImage>?,
        @SerializedName("goods_info") val goodsInfo: Goods?,
        @SerializedName("share") val share: Share?,
        @SerializedName("seller_popular_style") val sellerPopularStyle: SellerPopularStyle?,
        @SerializedName("goods_detail") val goodsDetail: String?,
        @SerializedName("seller_recommend_goods") val sellerRecommendGoods: List<Goods>?,
        @SerializedName("tag_list") val tagList: List<Tag>?,
        @SerializedName("seller_popular_goods") val sellerPopularGoods: List<Goods>?,
        @SerializedName("recommend_goods") val recommendGoods: List<Goods>?,
        @SerializedName("popular_goods") val popularGoods: PopularGoods?,
        @SerializedName("order_info") val orderInfo: OrderInfo?
    ) {
        data class TopImage(
            @SerializedName("image_url") private val imageUrl: String?
        ){
            val imgUrl: String get() {
                return if (!imageUrl.isNullOrEmpty() && imageUrl.startsWith("http")) {
                    imageUrl
                } else {
                    "http:$imageUrl"
                }
            }
        }

        data class Share(
            @SerializedName("share_img_path") val shareImgPath: String?,
            @SerializedName("share_url") val shareUrl: String?
        )

        data class SellerPopularStyle(
            @SerializedName("title") val title: String?,
            @SerializedName("goods_list") val goodsList: List<Goods>?
        )
        data class Tag(
            @SerializedName("tag_nm") val tagNm: String?,
            @SerializedName("tag_link") val tagLink: String?
        )

        data class PopularGoods(
            @SerializedName("title") val title: String?,
            @SerializedName("goods_list") val goodsList: List<Goods>?
        )

        data class OrderInfo(
            @SerializedName("bank_info") val bankInfo: String?,
            @SerializedName("account_info") val accountInfo: String?,
            @SerializedName("bank_owner") val bankOwner: String?,
            @SerializedName("address") val address: String?,
            @SerializedName("business_name") val businessName: String?,
            @SerializedName("representative") val representative: String?,
            @SerializedName("business_number") val businessNumber: String?,
            @SerializedName("mail_order_number") val mailOrderNumber: String?,
            @SerializedName("customer_center") val customerCenter: String?,
            @SerializedName("mail_address") val mailAddress: String?,
            @SerializedName("call_number") val callNumber: String?
        )
    }
}