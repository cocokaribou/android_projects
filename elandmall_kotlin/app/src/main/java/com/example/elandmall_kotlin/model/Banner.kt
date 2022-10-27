package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("ga_action") val gaAction: String?,
    @SerializedName("tr_yn") val trYn: String?,
    @SerializedName("shop_copy_nm") val shopCopyNm: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("rel_divi_cd") val relDiviCd: String?,
    @SerializedName("move_cont_no") val moveContNo: String?,
    @SerializedName("rel_no") val relNo: String?,
    @SerializedName("conts_dist_no") val contsDistNo: String?,
    @SerializedName("rel_cont_nm") val relContNm: String?,
    @SerializedName("link_url") val linkUrl: String?,
    @SerializedName("sale_area_no") val salesAreaNo: String?,
    @SerializedName("ga_category") val gaCategory: String?,
    @SerializedName("conts_divi_cd") val contsDiviCd: String?,
    @SerializedName("ga_label") val gaLabel: String?,
)