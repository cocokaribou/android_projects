package com.example.elandmall_kotlin.ui.main.viewholders

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCommonSortBinding
import com.example.elandmall_kotlin.ui.*

class CommonSortViewHolder(private val binding: ViewCommonSortBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonSortData)?.let {
            initUi(it)
        }
    }

    var isSpinnerTouched = false

    @SuppressLint("ClickableViewAccessibility")
    private fun initUi(data: ModuleData.CommonSortData) = with(binding) {
        val list = data.sortMap.keys.toTypedArray()
        val selected = list.indexOfFirst { it == data.sortSelected }

        spinner.apply {
            adapter = ArrayAdapter(binding.root.context, R.layout.view_common_sort_item, list)
            setSelection(selected)
            setOnTouchListener { _, _ ->
                isSpinnerTouched = true
                false
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (!isSpinnerTouched) return

                    if (data.sortSelected != parent?.selectedItem) {
                        EventBus.fire(StoreShopEvent(StoreShopEventType.SORT_CLICK, parent?.selectedItem!!))
                    }
                    isSpinnerTouched = false
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    isSpinnerTouched = false
                }
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
                EventBus.fire(StoreShopEvent(StoreShopEventType.GRID_CLICK))
            }
        }
    }
}