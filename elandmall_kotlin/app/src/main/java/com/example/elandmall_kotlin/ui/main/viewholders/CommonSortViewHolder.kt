package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCommonSortBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.util.Logger

typealias SortCallback = (Any) -> Unit
typealias GridCallback = () -> Unit
class CommonSortViewHolder(private val binding: ViewCommonSortBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonSortData)?.let {
            initUi(it)
        }
    }

    private fun initUi(data: ModuleData.CommonSortData) = with(binding) {
        if (data.isTopPaddingVisible) {
            padding.visibility = View.VISIBLE
        } else {
            padding.visibility = View.GONE
        }

        sort.apply {
            text = data.list[data.sortSelected ?: 0]
            setOnClickListener {
                BottomSheetFragment(
                    sortCallback = data.selectSort,
                    initIndex = data.sortSelected ?: 0,
                    list = data.list
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
                data.selectGrid()
            }
        }
    }
}