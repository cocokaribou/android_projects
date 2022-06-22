package com.example.shared_viewmodel.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.LogHelper
import com.example.shared_viewmodel.model.ModuleData

typealias Click = ((String) -> Unit)?
abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: ModuleData, listener: Click)

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