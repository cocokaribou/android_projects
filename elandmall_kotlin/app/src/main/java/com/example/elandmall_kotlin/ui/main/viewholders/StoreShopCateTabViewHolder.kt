package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopCateTabBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter.Companion.categoryAdapter
import com.example.elandmall_kotlin.util.Logger

class StoreShopCateTabViewHolder(private val binding: ViewStoreShopCateTabBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopCateTabData)?.let { it ->
            Logger.v("여기로 들어오긴 하지?")
            binding.list.adapter = categoryAdapter
            Logger.v("아이템 뭐야 ${categoryAdapter.currentList[0]}")
        }
    }
}