package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewHomeLuckyDealBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.tagUIType

class HomeLuckyDealViewHolder(private val binding: ViewHomeLuckyDealBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeLuckyDealData)?.let {
            drawGoodsUI(binding, it.homeLuckyDealData).tagUIType("home")
        }
    }
}