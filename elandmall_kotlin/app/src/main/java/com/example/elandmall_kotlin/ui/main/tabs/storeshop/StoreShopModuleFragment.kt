package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter.Companion.categoryAdapter

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    var categoryCount = 0
    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }
        viewModel.cateList.observe(this) {
            categoryCount = it?.size ?: 0
            categoryAdapter.submitList(it)
        }

        // holder click events
        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.CATEGORY_SCROLL -> {
                        // horizontal
                        with(binding.sticky.list.adapter as CategoryAdapter) {
                            tabSelector(it.content as Int / categoryCount + 1)
                        }
                        // vertical
                        scrollToY(it.content as Int)

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

    // sticky tab pager
    override fun setStickyVisible(isOn: Boolean) = with(binding) {
        sticky.list.visibility =
            if (isOn) View.VISIBLE
            else View.GONE
    }

    override fun selectTab(position: Int) {
        EventBus.fire(StoreShopEvent(position))
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