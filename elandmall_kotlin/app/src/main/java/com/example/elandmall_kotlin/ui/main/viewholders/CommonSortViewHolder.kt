package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCommonSortBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.ui.main.tabs.DialogType
import com.example.elandmall_kotlin.util.Logger

class CommonSortViewHolder(private val binding: ViewCommonSortBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonSortData)?.let {
            initUi(it)
        }
    }

    private fun initUi(data: ModuleData.CommonSortData) = with(binding) {
        val list = data.sortMap.keys.toTypedArray().toList()

        if (data.isTopPaddingVisible) {
            padding.visibility = View.VISIBLE
        } else {
            padding.visibility = View.GONE
        }

        sort.apply {
            text = data.sortSelected
            val dialogType = when (data.tabType) {
                TabType.PLAN_DETAIL -> DialogType.PLAN_DETAIL
                else -> DialogType.STORE_SHOP_SORT
            }
            setOnClickListener {
                BottomSheetFragment(
                    dialogType,
                    list
                ).show((binding.root.context as FragmentActivity).supportFragmentManager, "")
            }
        }

        val imgSource = when (data.gridSelected) {
            0 -> R.drawable.ic_btn_holder_grid
            1 -> R.drawable.ic_btn_holder_linear
            else -> R.drawable.ic_btn_holder_large
        }

        grid.apply {
            setImageResource(imgSource)
            setOnClickListener {
                EventBus.fire(
                    ViewHolderEvent(
                        eventType = ViewHolderEventType.GRID_CLICK,
                        tabType = data.tabType
                    )
                )
            }
        }
    }
}