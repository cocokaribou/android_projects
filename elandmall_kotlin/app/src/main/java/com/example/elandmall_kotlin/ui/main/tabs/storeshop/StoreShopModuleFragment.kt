package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    val cateAdapter by lazy { CategoryAdapter() }

    override fun observeData() {
        viewModel.storeShopList.observe(this) {
            viewModel.setStoreShopModules(it)
        }

        viewModel.uiList.observe(this) {
            setModules(it)
        }
        viewModel.cateList.observe(this) {
            cateAdapter.submitList(it)
        }

        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.SELECT_TAB -> {
                        // horizontal
                        with(binding.sticky.adapter as CategoryAdapter) {
                            scrollToX(it.pos.first)
                        }
                        // vertical
                        scrollToY(it.pos.second)

                    }
                    StoreShopEventType.CHANGE_VIEW_HOLDER -> {}
                }
            }
        }
    }

    // sticky tab pager
    override fun setStickyTab(isOn: Boolean) = with(binding.sticky) {
        visibility =
            if (isOn) View.VISIBLE
            else View.GONE

        adapter = cateAdapter
    }

    override fun selectTab(pair: Pair<Int,Int>) {
        Logger.v("횡 ${pair.first}")
        Logger.v("종 ${pair.second}")
        EventBus.fire(StoreShopEvent(pair))
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