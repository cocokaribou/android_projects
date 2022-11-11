package com.example.custom_logview.model

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("data") val data: Any?,
)
