package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewHomeTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class HomeTitleViewHolder(private val binding: ViewHomeTitleBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.HomeTitleData) {
        binding.title.text = data.title
        binding.subtitle.text = data.subTitle
    }
}