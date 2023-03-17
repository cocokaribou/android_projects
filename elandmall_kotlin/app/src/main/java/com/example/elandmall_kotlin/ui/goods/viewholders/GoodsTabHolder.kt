package com.example.elandmall_kotlin.ui.goods.viewholders

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.example.elandmall_kotlin.databinding.ViewGoodsTabBinding
import com.example.elandmall_kotlin.ui.goods.CustomGoodsTab
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.Logger
import kotlin.reflect.KFunction1

class GoodsTabHolder(val binding: ViewGoodsTabBinding) : GoodsBaseViewHolder(binding.root) {
    lateinit var goodsTab: CustomGoodsTab
    private var updateListener: KFunction1<Int, Unit>? = null

    var currentTab = 0
    override fun onBind(item: Any?) {
        Logger.v("여길 새로 타시는???")
        (item as? Map<*, *>)?.let {
            val listener = it["listener"] as? (Int) -> Unit
            val reviewCount = it["review_count"] as? Int
            val qnaCount = it["qna_count"] as? Int
            updateListener = it["update_listener"] as? KFunction1<Int, Unit>
            currentTab = it["current_tab"] as? Int ?: 0

            initUI(listener, reviewCount, qnaCount, updateListener)
        }
    }

    private fun initUI(listener: ((Int) -> Unit)?, reviewCount: Int?, qnaCount: Int?, updateListener: KFunction1<Int, Unit>?) =
        with(binding) {
            goodsTab = CustomGoodsTab(root.context, tabListener = listener, updateListener = updateListener).apply {
                updateCount(
                    reviewCount,
                    qnaCount
                )
            }
            root.addView(goodsTab)
            updateTab(currentTab)
        }

    private fun updateTab(index: Int) {
        updateListener?.invoke(index)
        goodsTab.updateTab(index)
    }
}

