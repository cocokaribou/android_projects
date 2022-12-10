package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewCommonCenterTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class CommonCenterTitleViewHolder(private val binding: ViewCommonCenterTitleBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonCenterTitleData)?.let {
            binding.title.text = it.title
        }
    }
}