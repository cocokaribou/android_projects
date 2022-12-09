package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopCateTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopCateTitleViewHolder(private val binding: ViewStoreShopCateTitleBinding):BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopCateTitleData)?.let {
            binding.content.text = it.text
        }
    }
}