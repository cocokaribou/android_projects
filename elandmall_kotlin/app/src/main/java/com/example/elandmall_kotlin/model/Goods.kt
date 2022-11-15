package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class Goods(
    @SerializedName("flag_img_path") val flagImgPath: String?,
    @SerializedName("ga_action") val gaAction: String?,
    @SerializedName("goods_nm") val goodsNm: String?,
    @SerializedName("wish_zzim_Yn") val wishZzimYn: String?,
    @SerializedName("flag_yn") val flagYn: String?,
    @SerializedName("goods_no") val goodsNo: String?,
    @SerializedName("brand_nm") val brandNm: String?,
    @SerializedName("icon_view") val iconView: String?,
    @SerializedName("cart_grp_cd") val cartGrpCd: String?,
    @SerializedName("conts_dist_no") val contsDistNo: String?,
    @SerializedName("cust_sale_price") val custSalePrice: Int?,
    @SerializedName("sale_qty") val saleQty: Int?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("wish_cart_Yn") val wishCartYn: String?,
    @SerializedName("dlp_category") val dlpCategory: String?,
    @SerializedName("conts_divi_cd") val contsDiviCd: String?,
    @SerializedName("ga_label") val gaLabel: String?,
    @SerializedName("field_recev_poss_yn") val fieldRecevPossYn: String?,
    @SerializedName("no_image_url") val noImageUrl: String?,
    @SerializedName("tr_yn") val trYn: String?,
    @SerializedName("sale_rate") val saleRate: Int?,
    @SerializedName("image_url") private val imgUrl: String?,
    @SerializedName("sale_shop_divi_cd") val saleShopDiviCd: String?,
    @SerializedName("rel_divi_cd") val relDiviCd: String?,
    @SerializedName("sale_price") val salePrice: Int?,
    @SerializedName("rel_no") val relNo: String?,
    @SerializedName("disp_ctg_no") val dispCtgNo: String?,
    @SerializedName("vir_vend_no") val virVendNo: String?,
    @SerializedName("quick_deli_poss_yn") val quickDeliPossYn: String?,
    @SerializedName("dlp_list") val dlpList: String?,
    @SerializedName("market_price") val marketPrice: Int?,
    @SerializedName("sale_area_no") val saleAreaNo: String?,
    @SerializedName("ga_category") val gaCategory: String?
) {
    val imageUrl:String
        get() {
            return if (!imgUrl.isNullOrEmpty() && imgUrl.startsWith("http")) {
                imgUrl
            } else {
                "http:$imgUrl"
            }
        }
}