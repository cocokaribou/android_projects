package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class SignedKeyResponse(
    @SerializedName("data") val data: Boolean? = null
): BaseResponse()