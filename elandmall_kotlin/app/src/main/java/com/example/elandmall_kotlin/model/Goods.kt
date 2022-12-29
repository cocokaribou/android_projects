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
    var rank: Int = -1
) {
    val imageUrl:String
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
}