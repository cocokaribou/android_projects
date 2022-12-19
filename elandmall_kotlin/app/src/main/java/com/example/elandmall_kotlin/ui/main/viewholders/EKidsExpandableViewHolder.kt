package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewEkidsExpandableBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.util.Logger

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
                EventBus.fire(ViewHolderEvent(ViewHolderEventType.TOGGLE_MORE1, TabType.EKIDS))
            }

        } else {
            title.text = if (data.isExpanded) BaseApplication.context.resources.getString(R.string.close) else BaseApplication.context.resources.getString(R.string.more)

            root.setOnClickListener {
                EventBus.fire(ViewHolderEvent(ViewHolderEventType.TOGGLE_MORE2, TabType.EKIDS))
            }
        }
    }
}