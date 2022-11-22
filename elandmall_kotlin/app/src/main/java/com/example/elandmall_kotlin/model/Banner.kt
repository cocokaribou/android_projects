package com.example.elandmall_kotlin.model

import com.google.gson.annotations.SerializedName

class Banner(
    @SerializedName("image_url") private val imgUrl: String?,
    @SerializedName("link_url") val linkUrl: String?,
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