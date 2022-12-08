package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopCateTabBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopCategoryAdapter.Companion.storeShopCateAdapter

class StoreShopCateTabViewHolder(private val binding: ViewStoreShopCateTabBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        binding.list.adapter = storeShopCateAdapter
    }
}