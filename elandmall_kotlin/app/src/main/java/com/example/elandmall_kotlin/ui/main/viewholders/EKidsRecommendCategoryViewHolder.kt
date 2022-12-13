package com.example.elandmall_kotlin.ui.main.viewholders

import android.widget.TextView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewEkidsRecommendCategoryBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.dpToPx

class EKidsRecommendCategoryViewHolder(private val binding: ViewEkidsRecommendCategoryBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsRecommendCategoryData)?.let {
            initUI(it.categoryList)
        }
    }

    private fun initUI(data: List<String>) = with(binding){
        categoryList.removeAllViews()

        data.forEach { category ->
            categoryList.addView(
                TextView(root.context, null, 0, R.style.home_tag_style).apply {
                    text = category
                    setBackgroundResource(R.drawable.tag_bg)
                    setPadding(7.dpToPx(), (4.5).toFloat().dpToPx(), 7.dpToPx(), (4.5).toFloat().dpToPx())
                }
            )
        }

    }
}