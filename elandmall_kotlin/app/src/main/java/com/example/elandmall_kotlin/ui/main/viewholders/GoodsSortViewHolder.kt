package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsSortBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.util.Logger

class GoodsSortViewHolder(private val binding: ViewGoodsSortBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.GoodsSortData)?.let {
            initUi(it)
        }
    }

    var clicked = 0
    private fun initUi(data: ModuleData.GoodsSortData) = with(binding){

        list.adapter = ArrayAdapter(binding.root.context, R.layout.view_goods_sort_item, data.goodsSortMap.keys.toTypedArray())
        list.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                EventBus.fire(StoreShopEvent(StoreShopEventType.SORT_CLICK, parent?.selectedItem ?: ""))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        grid.setOnClickListener {
            if (clicked >= 2) {
                clicked = 0
            } else {
                clicked++
            }

            when (clicked) {
                0 -> binding.grid.setImageResource(R.drawable.ic_btn_holder_grid)
                1 -> binding.grid.setImageResource(R.drawable.ic_btn_holder_linear)
                2 -> binding.grid.setImageResource(R.drawable.ic_btn_holder_large)
            }
            EventBus.fire(StoreShopEvent(StoreShopEventType.GRID_CLICK, clicked))
        }
    }
}