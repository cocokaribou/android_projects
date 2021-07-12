package com.siv.du.data

import com.google.gson.annotations.SerializedName

data class PushData(
    @field:SerializedName("msgTag") private var msgTag: String = "",
    @field:SerializedName("date") var date: String = "",
    @field:SerializedName("title") var title: String = "",
    @field:SerializedName("content") var content: String = "",
    @field:SerializedName("opened") var opened: String = "",
    @field:SerializedName("mode") var mode: String = "",
    @field:SerializedName("imgCheck") var imgCheck: String = "",
    @field:SerializedName("imgUrl") var imgUrl: String = "",
    @field:SerializedName("labelCode") var labelCode: String = "",
    @field:SerializedName("link") var link: String = ""

)
