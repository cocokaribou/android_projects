package com.example.aos_framework_demo

import com.google.gson.annotations.SerializedName

data class SeoulVO(

    @field:SerializedName("DESCRIPTION") var DESCRIPTION: Description,
//    @field:SerializedName("DATA") var DATA: Data
    @field:SerializedName("DATA") var DATA: ArrayList<Data>

) {
    data class Description(
        @field:SerializedName("UPSO_NM") var UPSO_NM: String,
        @field:SerializedName("CGG_CODE") var CGG_CODE: String,
        @field:SerializedName("CGG_CODE_NM") var CGG_CODE_NM: String,
        @field:SerializedName("RDN_CODE_NM") var RDN_CODE_NM: String,
    )
    data class Data(
        @field:SerializedName("upso_nm") var upso_nm: String,
        @field:SerializedName("cgg_code") var cgg_code: String,
        @field:SerializedName("cgg_code_nm") var cgg_code_nm: String,
        @field:SerializedName("crtfc_yn") var crtfc_yn: String,
        @field:SerializedName("rdn_code_nm") var rdn_code_nm: String,
        @field:SerializedName("crt_time") var crt_time: String?,
        @field:SerializedName("upd_time") var upd_time: String?,

    )
}