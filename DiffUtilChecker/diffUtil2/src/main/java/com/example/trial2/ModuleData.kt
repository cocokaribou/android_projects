package com.example.trial2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.util.*

sealed class ModuleData {
    abstract fun clone(): ModuleData
    abstract val id: UUID

    data class Module1(
        val title: String,
        val checker: String,
        val itemList: List<String>,
        override val id: UUID = UUID.randomUUID()

    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }

    data class Module2(
        val title: String,
        val checker: String,
        val itemList: List<String>,
        override val id: UUID = UUID.randomUUID()

    ) : ModuleData() {
        override fun clone(): ModuleData {
            return copy()
        }

        companion object
    }
}

abstract class ModuleBaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: Any, position: Int)
}