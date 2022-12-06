package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewGoodsLargeBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI

class GoodsLargeViewHolder(private val binding: ViewGoodsLargeBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.GoodsLargeData)?.let {
            drawGoodsUI(binding, it.goodsData)
        }
    }
}