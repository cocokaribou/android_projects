package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("goods_list") val goodsList: List<Goods?>?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("link_url") val linkUrl: String?
)