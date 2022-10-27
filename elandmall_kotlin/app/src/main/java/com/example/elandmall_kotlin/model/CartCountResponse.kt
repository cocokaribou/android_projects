package com.example.elandmall_kotlin.model

import com.example.elandmall_kotlin.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class CartCountResponse(
    @SerializedName("cart_count") val cartCount: Int?
)