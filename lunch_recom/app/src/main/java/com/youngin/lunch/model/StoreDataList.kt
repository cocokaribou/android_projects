package com.youngin.lunch.model

import com.google.gson.annotations.SerializedName

data class StoreDataList(
    @field:SerializedName("storeList") var stoList: ArrayList<StoreData>?
) {
    data class StoreData(
        @field:SerializedName("stoImgUrl") var stoImgUrl: String?,
    )
}
