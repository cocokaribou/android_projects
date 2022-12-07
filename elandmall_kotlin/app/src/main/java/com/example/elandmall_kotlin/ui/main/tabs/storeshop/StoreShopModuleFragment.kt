package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter.Companion.categoryAdapter
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx
import kotlin.math.round

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)

            // sticky header
            binding.sticky.adapter = categoryAdapter
        }
        // holder click events
        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.CATEGORY_SCROLL -> {
                        Logger.v("선택!!! ${it.content as Int}")
                        scrollToY(it.content)
                        // TODO
                    }
                    StoreShopEventType.GRID_CLICK -> {
                        viewModel.updateGrid()
                    }
                    StoreShopEventType.SORT_CLICK -> {
                        viewModel.updateSort(it.content as String)
                    }
                    StoreShopEventType.STORE_CLICK -> {
                        viewModel.updateStore(it.content as StoreShopResponse.StorePick)
                    }
                }
            }
        }
    }

    override fun selectTab(position: Int) {
        val double = position.toDouble() / viewModel.cateCount.toDouble()
        val selected = (round(double) - 2).toInt()
        categoryAdapter.tabSelector(selected)

        val halfScreen = getScreenWidthToPx()/2
        val halfHolder = getScreenWidthToPx()/10
        (binding.sticky.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(selected, halfScreen - halfHolder)
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            StoreShopModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}