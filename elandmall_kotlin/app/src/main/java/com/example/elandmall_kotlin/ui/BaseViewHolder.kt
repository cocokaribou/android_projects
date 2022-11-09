package com.example.elandmall_kotlin.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun onBind() {

    }
}