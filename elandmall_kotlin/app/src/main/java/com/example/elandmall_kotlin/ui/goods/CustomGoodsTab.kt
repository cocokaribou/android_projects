package com.example.elandmall_kotlin.ui.goods

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.elandmall_kotlin.databinding.ViewGoodsTabStickyBinding
import com.google.android.material.resources.TextAppearance
import com.google.android.material.tabs.TabLayout
import kotlin.reflect.KFunction1

class CustomGoodsTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0,
    val tabListener: ((Int) -> Unit)? = {},
    val updateListener: KFunction1<Int, Unit>?
) : LinearLayout(context, attrs, def) {

    private val binding by lazy { ViewGoodsTabStickyBinding.inflate(LayoutInflater.from(context), this, true) }

    private val tabList = listOf(
        binding.tabs.newTab().setText("상품정보"),
        binding.tabs.newTab().setText("리뷰(0)"),
        binding.tabs.newTab().setText("Q&A(0)"),
        binding.tabs.newTab().setText("주문정보")
    )

    init {
        tabList.forEach {
            binding.tabs.addTab(it)
        }

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tabListener?.invoke(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun updateCount(reviewCount: Int?, qnaCount: Int?) {
        binding.tabs.getTabAt(1)?.apply { text = "리뷰($reviewCount)" }
        binding.tabs.getTabAt(2)?.apply { text = "Q&A($qnaCount)" }
    }

    fun updateTab(index: Int) {
        updateListener?.invoke(index)
        binding.tabs.selectTab(tabList[index])
    }
}