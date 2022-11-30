package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStoreShopCateTabBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter

class StoreShopCateTabViewHolder(private val binding: ViewStoreShopCateTabBinding) : BaseViewHolder(binding.root) {
    val cateAdapter by lazy { CategoryAdapter() }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopCateTabData)?.let { it ->
            initUI(it.storeShopCateList)
        }
    }

    private fun initUI(data: List<StoreShopResponse.CategoryGoods>) = with(binding) {
        list.adapter = cateAdapter
        cateAdapter.submitList(data)
    }
}