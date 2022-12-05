package com.example.elandmall_kotlin.ui.main.viewholders

import android.widget.ArrayAdapter
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsSortBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class GoodsSortViewHolder(private val binding: ViewGoodsSortBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.GoodsSortData)?.let {
            initUi(it)
        }
    }

    private fun initUi(data: ModuleData.GoodsSortData) = with(binding){

        list.adapter = ArrayAdapter(binding.root.context, R.layout.view_goods_sort_item, data.goodsSortMap.keys.toTypedArray())
    }
}