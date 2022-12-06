package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import com.example.elandmall_kotlin.databinding.ViewStoreShopRegularBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopRegularViewHolder(private val binding: ViewStoreShopRegularBinding):BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopRegularData)?.let {
            initUI(it.storeShopRegularData)
        }
    }

    private fun initUI(data: List<StoreShopResponse.MyRegularStore>) = with(binding){
        if (data.isEmpty()) {
            emptyRegularLay.visibility = View.VISIBLE
            regularLay.visibility = View.GONE
        } else {
            emptyRegularLay.visibility = View.GONE
            regularLay.visibility = View.VISIBLE
        }
    }
}