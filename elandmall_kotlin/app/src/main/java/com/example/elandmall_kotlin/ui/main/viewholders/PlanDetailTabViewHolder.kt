package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewPlanDetailTabTitleBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class PlanDetailTabTitleViewHolder(private val binding: ViewPlanDetailTabTitleBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.PlanDetailTabTitleData)?.let {
            binding.title.text = it.title
        }
    }
}