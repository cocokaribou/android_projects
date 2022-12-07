package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewStorePickMoreBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class StorePickMoreViewHolder(private val binding: ViewStorePickMoreBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StorePickMoreData)?.let {
            binding.content.text = it.storeSelected + " 바로가기 >"
        }
    }
}