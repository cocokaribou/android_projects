package com.example.elandmall_kotlin.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(item: Any, pos: Int)

    open fun onAppeared() {
        // NOP on root
    }

    open fun onDisappeard() {
        // NOP on root
    }
}