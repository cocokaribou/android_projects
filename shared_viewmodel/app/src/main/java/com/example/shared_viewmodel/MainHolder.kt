package com.example.shared_viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.model.MainData
import com.example.shared_viewmodel.ui.list.Click

abstract class MainHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: MainData, listener: Click)

    open fun onAppeared() {
        LogHelper.v("appeared $this")
    }

    open fun onDisappeared() {
        LogHelper.v("disappeared $this")
    }

    open fun onRecycled() {
        LogHelper.v("onRecycled $this")
    }
}