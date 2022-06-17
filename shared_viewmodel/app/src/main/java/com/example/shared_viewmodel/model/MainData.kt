package com.example.shared_viewmodel.model

import com.example.shared_viewmodel.ui.list.ModulesType
import com.google.gson.annotations.SerializedName

data class MainData(
    var type : ModulesType,
    var data: StoreData? = null,
    var dataList: List<StoreData>? = null
) {
    fun clone() : MainData {
        return copy()
    }
}