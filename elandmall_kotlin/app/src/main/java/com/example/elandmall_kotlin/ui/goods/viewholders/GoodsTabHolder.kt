package com.example.elandmall_kotlin.ui.goods.viewholders

import com.example.elandmall_kotlin.databinding.ViewGoodsTabBinding
import com.example.elandmall_kotlin.view.GoodsCommonTabView
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.view.TabListener
import kotlin.reflect.KFunction1

class GoodsTabHolder(val binding: ViewGoodsTabBinding) : GoodsBaseViewHolder(binding.root), TabListener {
    var goodsTab: GoodsCommonTabView = GoodsCommonTabView(binding.root.context)
    private var updateTabInner: KFunction1<Int, Unit>? = null

    init {
        goodsTab.setCallback(this@GoodsTabHolder)
        binding.root.addView(goodsTab)
    }

    private var currentIndex = 0
    override fun onBind(item: Any?) {
        (item as? Map<*, *>)?.let {
            val reviewCount = it["reviewCount"] as? Int
            val qnaCount = it["qnaCount"] as? Int
            updateTabInner = it["updateTabInner"] as? KFunction1<Int, Unit>
            currentIndex = it["currentIndex"] as? Int ?: 0

            initUI(reviewCount, qnaCount)
        }
    }

    private fun initUI(reviewCount: Int?, qnaCount: Int?) {
        goodsTab.apply {
            updateCount(reviewCount, qnaCount)
            selectTab(currentIndex)
            Logger.v("홀더에서 따라감 $currentIndex")
        }
        updateTabInner?.invoke(currentIndex)
    }

    override fun onTabSelect(index: Int) {
        Logger.v("홀더에서 누름!")
        updateTabInner?.invoke(index)
    }
}

