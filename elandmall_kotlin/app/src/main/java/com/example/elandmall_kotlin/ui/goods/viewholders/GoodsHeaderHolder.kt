package com.example.elandmall_kotlin.ui.goods.viewholders

import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.LinkEventType
import com.example.elandmall_kotlin.databinding.ViewGoodsHeaderBinding
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder

class GoodsHeaderHolder(val binding: ViewGoodsHeaderBinding): GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        initUI()
    }

    private fun initUI() = with(binding) {
        back.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.HOME))
        }
        home.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.HOME))
        }
        search.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
        }
        cart.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.DEFAULT))
        }
    }
}