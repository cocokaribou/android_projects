package com.example.elandmall_kotlin.ui.main.viewholders

import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeMultiBannerBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.AdjustHeightImageViewTarget

class HomeMultiBannerViewHolder(private val binding: ViewHomeMultiBannerBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeMultiBannerData)?.let {
            initView(it.homeBannerData)
        }
    }

    private fun initView(list: List<Banner>) = with(binding){
        list[0].let { data ->
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .fitCenter()
                .into(AdjustHeightImageViewTarget(banner1))

            banner1.setOnClickListener {
                EventBus.fire(LinkEvent(data.linkUrl))
            }
        }
        list[1].let { data ->
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .fitCenter()
                .into(AdjustHeightImageViewTarget(banner2))
            banner2.setOnClickListener {
                EventBus.fire(LinkEvent(data.linkUrl))
            }
        }
    }
}