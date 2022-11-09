package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class CartCountResponse(
    @SerializedName("cart_count") val cartCount: Int?
)