package com.example.elandmall_kotlin.ui.main

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.elandmall_kotlin.base.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class CommonModulesAdapter(private val lifeCycleOwner: LifecycleOwner): ListAdapter<ModuleData, BaseViewHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<ModuleData>() {
            override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}