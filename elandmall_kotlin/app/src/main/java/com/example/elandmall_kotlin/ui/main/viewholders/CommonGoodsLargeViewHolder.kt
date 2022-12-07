package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewCommonGoodsLargeBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI

class CommonGoodsLargeViewHolder(private val binding: ViewCommonGoodsLargeBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonGoodsLargeData)?.let {
            drawGoodsUI(binding, it.goodsData)
        }
    }
}