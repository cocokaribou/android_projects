package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopCateNameBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopCateNameViewHolder(private val binding: ViewStoreShopCateNameBinding):BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopCateNameData)?.let {
            binding.content.text = it.text
        }
    }
}