package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import com.example.elandmall_kotlin.databinding.ViewCommonGoodsGridBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.tagUIType
import com.example.elandmall_kotlin.util.Logger

class CommonGoodsGridViewHolder(private val binding: ViewCommonGoodsGridBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonGoodsGridData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.CommonGoodsGridData) =with(binding){
//        if (!data.isDividerVisible) {
//            divider1.visibility = View.GONE
//            divider2.visibility = View.GONE
//        } else {
//            divider1.visibility = View.VISIBLE
//            divider2.visibility = View.VISIBLE
//        }

        val item1 = data.goodsListData[0]
        val item2: Goods? =
            if (data.goodsListData.size == 2) data.goodsListData[1] else null

        drawGoodsUI(holder1, item1).tagUIType("storeshop")

        item2?.let {
            holder2.root.visibility = View.VISIBLE
            drawGoodsUI(holder2, it).tagUIType("storeshop")
        }?: run {
            holder2.root.visibility = View.GONE
        }
    }
}