package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCommonCenterTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx

class CommonCenterTitleViewHolder(private val binding: ViewCommonCenterTitleBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonCenterTitleData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonCenterTitleData) = with(binding) {
        title.text = data.title
        when (data.titleStyle) {
            "plan_detail" -> {
                title.textSize = 15f
                title.setPadding(0, 5.dpToPx(), 0, 5.dpToPx())
            }
            "ekids" -> {
                title.textSize = 16f
                title.setPadding(0, 50.dpToPx(), 0, 15.dpToPx())
            }
        }

        if (data.isDividerVisible) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.GONE
        }
    }
}