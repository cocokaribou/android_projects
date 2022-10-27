package com.example.elandmall_kotlin.base

import com.google.gson.annotations.SerializedName

abstract class BaseResponse(
    @SerializedName("resultMsg") val resultMsg: String? = "",
    @SerializedName("code") val resultCd: String? = "",
){
    fun isSuccessful():Boolean = resultCd != "0000"
}