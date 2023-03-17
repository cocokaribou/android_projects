package com.example.elandmall_kotlin.ui.goods.viewholders.tab4

import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsOrderInfoBinding
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder

class GoodsOrderInfoHolder(val binding: ViewGoodsOrderInfoBinding) : GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        // hard coded info
        initUI()
    }

    private fun initUI() = with(binding){
        info.text = root.context.getString(R.string.goods_order_info)
    }
}