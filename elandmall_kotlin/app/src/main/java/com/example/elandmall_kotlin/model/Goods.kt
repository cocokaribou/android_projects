package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

class Goods(
    @SerializedName("flag_img_path") private val flagImgPath: String?,
    @SerializedName("goods_nm") val goodsNm: String?,
    @SerializedName("brand_nm") val brandNm: String?,
    @SerializedName("icon_view") val iconView: String?,
    @SerializedName("cust_sale_price") val custSalePrice: Int?,
    @SerializedName("sale_qty") val saleQty: Int?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("sale_rate") val saleRate: Int?,
    @SerializedName("image_url") private val imgUrl: String?,
    @SerializedName("sale_price") val salePrice: Int?,
    @SerializedName("market_price") val marketPrice: Int?,
    @SerializedName("goods_comment_count") val goodsCommentCount: Int?,
    @SerializedName("goods_star_point") val goodsStarPoint: Int?,
    // home tab > time sale
    @SerializedName("title") val title: String?,
    @SerializedName("time") val time: String?,
    // best tab
    var rank: Int = -1,
    // goods detail
    @SerializedName("brand_image_url") val brandImageUrl: String?,
    @SerializedName("final_price") val finalPrice: Int?,
    @SerializedName("coupon_sale_price") val couponSalePrice: Int?,
    @SerializedName("goods_review_info") val goodsReviewInfo: GoodsReviewInfo?,
    @SerializedName("goods_question_info") val goodsQuestionInfo: GoodsQuestionInfo?,
    @SerializedName("point") val point: String?
) {
    val imageUrl: String
        get() {
            return if (!imgUrl.isNullOrEmpty() && imgUrl.startsWith("http")) {
                imgUrl
            } else {
                "http:$imgUrl"
            }
        }

    val flagImgUrl: String
        get() {
            return if (!flagImgPath.isNullOrEmpty() && flagImgPath.startsWith("http")) {
                flagImgPath
            } else {
                "http:$flagImgPath"
            }
        }

    data class GoodsReviewInfo(
        @SerializedName("review_count") val reviewCount: Int?,
        @SerializedName("review_info") val reviewInfo: ReviewInfo?
    ) {
        data class ReviewInfo(
            @SerializedName("review_image_info") val reviewImageInfo: ReviewImageInfo?,
            @SerializedName("review_text_info") val reviewTextInfo: ReviewTextInfo?
        ) {
            data class ReviewImageInfo(
                @SerializedName("review_count") val reviewCount: Int?,
                @SerializedName("review_list") val reviewList: List<Review?>?,
                @SerializedName("review_more_url") val reviewMoreUrl: String?
            ) {
                data class Review(
                    @SerializedName("image_url") val imageUrl: String?,
                    @SerializedName("userID") val userID: String?,
                    @SerializedName("height") val height: String?,
                    @SerializedName("weight") val weight: String?,
                    @SerializedName("purchase_goods_info") val purchaseGoodsInfo: String?,
                    @SerializedName("review_comment") val reviewComment: String?,
                    @SerializedName("link_url") val linkUrl: String?
                )
            }

            data class ReviewTextInfo(
                @SerializedName("review_count") val reviewCount: Int?,
                @SerializedName("review_list") val reviewList: List<Review?>?,
                @SerializedName("review_more_url") val reviewMoreUrl: String?
            ) {
                data class Review(
                    @SerializedName("userID") val userID: String?,
                    @SerializedName("height") val height: String?,
                    @SerializedName("weight") val weight: String?,
                    @SerializedName("purchase_goods_info") val purchaseGoodsInfo: String?,
                    @SerializedName("review_comment") val reviewComment: String?
                )
            }
        }
    }

    data class GoodsQuestionInfo(
        @SerializedName("question_count") val questionCount: Int?,
        @SerializedName("question_list") val questionList: List<Question?>?,
        @SerializedName("question_more_url") val questionMoreUrl: String?
    ) {
        data class Question(
            @SerializedName("userID") val userID: String?,
            @SerializedName("date") val date: String?,
            @SerializedName("content") val content: String?,
            @SerializedName("secret") val secret: String?
        )
    }
}