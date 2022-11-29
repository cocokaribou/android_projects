package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class TitleViewHolder(private val binding: ViewTitleBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.TitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.TitleData) {
        binding.title.text = data.title
        binding.subtitle.text = data.subTitle
    }
}