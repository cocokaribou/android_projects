package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopCategoryAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx
import kotlin.math.round

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)

            // sticky header
            binding.sticky.adapter = storeShopCateAdapter
        }
        // holder click events
        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.CATEGORY_SCROLL -> {
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