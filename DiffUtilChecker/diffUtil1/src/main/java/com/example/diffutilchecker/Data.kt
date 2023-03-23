package com.example.diffutilchecker

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*

sealed class Data(
    var id: UUID,
    var typeEnum: DATA_TYPE
) {
    data class Data1(
        val itemList: List<String>
    ) : Data(id = UUID.randomUUID(), DATA_TYPE.TYPE1) {
        companion object
    }

    data class Data2(
        val itemList: List<String>
    ) : Data(id = UUID.randomUUID(), DATA_TYPE.TYPE2) {
        companion object
    }
}


enum class DATA_TYPE {
    TYPE1,
    TYPE2
}


abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: Any, position: Int)
}