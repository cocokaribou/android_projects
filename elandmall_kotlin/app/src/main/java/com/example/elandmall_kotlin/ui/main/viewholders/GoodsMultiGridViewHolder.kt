package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewGoodsMultiGridBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.Logger

class GoodsMultiGridViewHolder(private val binding:ViewGoodsMultiGridBinding):BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.GoodsMultiGridData)?.let {
            binding.holder1.text = it.goodsListData[0].goodsNm
            binding.holder2.text = it.goodsListData[1].goodsNm
        }
    }
}