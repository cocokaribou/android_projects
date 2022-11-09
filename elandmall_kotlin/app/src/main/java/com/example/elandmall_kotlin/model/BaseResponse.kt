package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

abstract class BaseResponse(
    @SerializedName("resultMsg") val resultMsg: String? = "",
    @SerializedName("code") val resultCd: String? = "",
){
    fun isSuccessful():Boolean = resultCd != "0000"
}