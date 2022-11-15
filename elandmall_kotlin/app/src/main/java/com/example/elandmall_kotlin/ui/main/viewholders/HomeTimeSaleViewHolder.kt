package com.example.elandmall_kotlin.ui.main.viewholders

import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeTimeSaleBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class HomeTimeSaleViewHolder(private val binding: ViewHomeTimeSaleBinding, val lifecycleOwner: LifecycleOwner) :
    BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeTimeData)?.homeTimeData?.let {
            initView(it)
        }
    }

    fun initView(data: Goods) = with(binding){

        // Image
        Glide.with(itemView.context)
            .load(data.imageUrl)
            .into(goodsImg)
    }
}