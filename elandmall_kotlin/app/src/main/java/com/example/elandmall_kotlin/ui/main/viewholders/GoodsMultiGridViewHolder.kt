package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import com.example.elandmall_kotlin.databinding.ViewGoodsMultiGridBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.tagUIType

class GoodsMultiGridViewHolder(private val binding: ViewGoodsMultiGridBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.GoodsMultiGridData)?.let {
            initUI(it.goodsListData)
        }
    }

    private fun initUI(data: List<Goods>) =with(binding){
        val item1 = data[0]
        val item2: Goods? =
            if (data.size == 2) data[1] else null

        drawGoodsUI(holder1, item1).tagUIType("storeshop")

        item2?.let {
            holder2.root.visibility = View.VISIBLE
            drawGoodsUI(holder2, it).tagUIType("storeshop")
        }?: run {
            holder2.root.visibility = View.GONE
        }
    }
}