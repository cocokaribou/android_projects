package com.cocokaribou.recycler_view_staggered_grid

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoVO(
    @field:SerializedName("id") var id: String = "",
    @field:SerializedName("download_url") var photoUrl: String = "",
) : Parcelable

