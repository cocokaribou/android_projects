package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter.Companion.categoryAdapter
import com.example.elandmall_kotlin.util.Logger

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    var categoryCount = 0
    override fun observeData() {
        viewModel.storeShopList.observe(this) {
            viewModel.setStoreShopModules(it)
        }

        viewModel.uiList.observe(this) {
            setModules(it)
        }
        viewModel.cateList.observe(this) {
            categoryCount = it.size
            categoryAdapter.submitList(it)
        }

        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.SELECT_TAB -> {
                        Logger.v("들어오니... 하.. ${it.pos}")
                        // horizontal
                        with(binding.sticky.list.adapter as CategoryAdapter) {
                            tabSelector(it.pos / categoryCount + 1)
                        }
                        // vertical
                        scrollToY(it.pos)

                    }
                    StoreShopEventType.CHANGE_VIEW_HOLDER -> {}
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