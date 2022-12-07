package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewCommonTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class CommonTitleViewHolder(private val binding: ViewCommonTitleBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonTitleData) {
        binding.title.text = data.title
        binding.subtitle.text = data.subTitle
    }
}