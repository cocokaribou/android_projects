package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopPickMoreBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.Logger

class StoreShopPickMoreViewHolder(private val binding: ViewStoreShopPickMoreBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopPickMoreData)?.let {
            binding.content.text = it.text
        }
    }
}