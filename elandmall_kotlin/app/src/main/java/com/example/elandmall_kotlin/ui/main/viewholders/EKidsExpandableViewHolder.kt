package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewEkidsExpandableBinding
import com.example.elandmall_kotlin.ui.*

class EKidsExpandableViewHolder(private val binding: ViewEkidsExpandableBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsExpandableData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.EKidsExpandableData) = with(binding){
        if ("weeklyBest".equals(data.viewType, true)) {
            title.text = if (data.isExpanded) BaseApplication.context.resources.getString(R.string.close) else BaseApplication.context.resources.getString(R.string.more)

            root.setOnClickListener {
                data.toggleExpand()
            }

        } else {
            title.text = if (data.isExpanded) BaseApplication.context.resources.getString(R.string.close) else BaseApplication.context.resources.getString(R.string.more)

            root.setOnClickListener {
                data.toggleExpand()
            }
        }
    }
}