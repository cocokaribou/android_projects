package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewStoreShopPickBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopPickViewHolder(private val binding: ViewStoreShopPickBinding):BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopPickData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.StoreShopPickData) {
        var holderType = 0
        binding.storeMore.setOnClickListener {
            data.clicker.invoke()
        }
    }
}