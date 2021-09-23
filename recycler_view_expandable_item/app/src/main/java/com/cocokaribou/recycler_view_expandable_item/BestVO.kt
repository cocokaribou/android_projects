package com.cocokaribou.recycler_view_expandable_item

import com.google.gson.annotations.SerializedName

data class BestVO(
    @field:SerializedName("goods_list") var goodsList: MutableList<Goods>
) {
    constructor() : this(mutableListOf<Goods>())

    data class Goods(
        @field:SerializedName("goods_no") var goodsNo: String = "",
        @field:SerializedName("goods_nm") var goodsNm: String = "",
        @field:SerializedName("brand_nm") var brandNm: String = "",
        @field:SerializedName("cust_sale_price") var salePrice: Int,
        @field:SerializedName("sale_rate") var saleRate: Int,
        @field:SerializedName("link_url") var linkUrl: String = "",
        @field:SerializedName("image_url") var imgUrl: String = "",
        @field:SerializedName("no_image_url") var noImgUrl: String = "",
        @field:SerializedName("sale_price") var marketPrice: Int,
    )
}
