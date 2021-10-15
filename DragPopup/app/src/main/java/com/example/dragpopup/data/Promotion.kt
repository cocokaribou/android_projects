package com.example.dragpopup.data

import com.google.gson.annotations.SerializedName

data class Promotion(
    @field:SerializedName("imgUrl") var imgUrl: String,
    @field:SerializedName("promoUrl") var promoUrl: String

)

