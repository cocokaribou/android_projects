package com.example.elandmall_kotlin.ui.goods.viewholders

import com.example.elandmall_kotlin.databinding.ViewGoodsTabBinding
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.google.android.material.tabs.TabLayout

class GoodsTabHolder(val binding: ViewGoodsTabBinding) : GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? Map<*, *>)?.let {
            val listener = it["listener"] as? (Int) -> Unit
            val reviewCount = it["review_count"] as? Int
            val qnaCount = it["qna_count"] as? Int

            initUI(listener, reviewCount, qnaCount)
        }
    }

    private fun initUI(listener: ((Int) -> Unit)?, reviewCount: Int?, qnaCount: Int?) = with(binding.tabs) {


        tabs.addTab(tabs.newTab().setText("상품정보"))
        tabs.addTab(tabs.newTab().setText("리뷰($reviewCount)"))
        tabs.addTab(tabs.newTab().setText("Q&A($qnaCount)"))
        tabs.addTab(tabs.newTab().setText("주문정보"))

        listener ?: return
        tabs.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                listener.invoke(tab?.position ?: 0)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}

