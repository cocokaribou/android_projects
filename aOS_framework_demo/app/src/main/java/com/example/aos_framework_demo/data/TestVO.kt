package com.example.aos_framework_demo.data

import com.google.gson.annotations.SerializedName

data class TestVO(
    @field:SerializedName("code") var code: String,
    @field:SerializedName("data") var data: Data,
    @field:SerializedName("resultMsg") var resultMsg: String

) {
    data class Data(
        @field:SerializedName("recommend_store_list") var recommend: ArrayList<Recommend>,
        @field:SerializedName("category_goods_list") var category: ArrayList<Category>
    ) {
        data class Recommend(
            @field:SerializedName("store_nm") var storeName: String,
            @field:SerializedName("image_url") var imgUrl: String,
            @field:SerializedName("link_url") var linkUrl: String,
        )

        data class Category(
            @field:SerializedName("goods_list") var goodsList: ArrayList<Goods>,
            @field:SerializedName("ctg_depth") var ctgDepth: String,
            @field:SerializedName("ctg_no") var ctgNo: String,
            @field:SerializedName("ctg_nm") var ctgNm: String,
            @field:SerializedName("active_img_url") var activeImgUrl: String,
            @field:SerializedName("dactive_img_url") var dactiveImgUrl: String,
        ) {
            data class Goods(
                @field:SerializedName("flag_img_path") var flagImgPath: String,
                @field:SerializedName("ga_action") var gaAction: String,
                @field:SerializedName("goods_nm") var goodsNm: String,
                @field:SerializedName("wish_zzim_Yn") var wishZzimYn: String,
                @field:SerializedName("flag_yn") var flagYn: String,
                @field:SerializedName("goods_no") var goodsNo: String,
                @field:SerializedName("brand_nm") var brandNm: String,
                @field:SerializedName("icon_view") var iconView: String,
                @field:SerializedName("cart_grp_cd") var cartGrpCd: String,
                @field:SerializedName("cust_sale_price") var custSalePrice: Int,
                @field:SerializedName("lot_deli_yn") var lotDeliYn: String,
                @field:SerializedName("link_url") var linkUrl: String,
                @field:SerializedName("wish_cart_Yn") var wishCartYn: String,
                @field:SerializedName("ga_label") var gaLabel: String,
                @field:SerializedName("field_recev_poss_yn") var fieldRecevPossYn: String,
                @field:SerializedName("no_image_url") var noImageUrl: String,
                @field:SerializedName("sale_rate") var saleRate: Int,
                @field:SerializedName("image_url") var imageUrl: String,
                @field:SerializedName("sale_shop_divi_cd") var saleShopDiviCd: String,
                @field:SerializedName("sale_price") var salePrice: Int,
                @field:SerializedName("disp_ctg_no") var dispCtgNo: String,
                @field:SerializedName("vir_vend_no") var virVendNo: String,
                @field:SerializedName("quick_deli_poss_yn") var quickDeliPossYn: String,
                @field:SerializedName("market_price") var marketPrice: Int,
                @field:SerializedName("ga_category") var gaCategory: String,
                @field:SerializedName("goods_comment_count") var goodsCommentCount: Int
            )
        }

    }

}