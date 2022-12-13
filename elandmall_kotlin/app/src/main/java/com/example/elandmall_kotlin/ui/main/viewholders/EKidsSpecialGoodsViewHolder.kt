package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewEkidsSpecialGoodsBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI

class EKidsSpecialGoodsViewHolder(private val binding: ViewEkidsSpecialGoodsBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsSpecialGoodsData)?.let {
            drawGoodsUI(binding, it.goodsData)
        }
    }
}