package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewEkidsExpandableBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class EKidsExpandableViewHolder(private val binding: ViewEkidsExpandableBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsExpandableData)?.let {
            binding.title.text = it.title
        }
    }
}