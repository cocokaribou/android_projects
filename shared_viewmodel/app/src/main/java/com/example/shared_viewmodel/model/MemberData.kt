package com.example.shared_viewmodel.model

import com.google.gson.annotations.SerializedName

data class MemberData(
    @field:SerializedName("mbrNo") val mbrNo: Int?,
    @field:SerializedName("mbrId") val mbrId: String?,
    @field:SerializedName("mbrName") val mbrName: String?,
    @field:SerializedName("mbrGrade") val mbrGrade: String?,
    @field:SerializedName("mbrAdmin") val mbrAdmin: String?,
    @field:SerializedName("mbrImg") val mbrImg: String?,
    @field:SerializedName("result") val result: String?,
    @field:SerializedName("msg") val msg: String?,
)
