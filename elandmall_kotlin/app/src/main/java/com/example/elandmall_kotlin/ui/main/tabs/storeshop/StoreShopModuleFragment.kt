package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.ViewHolderEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopStickyAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addScrollListener(scrollListener)
    }

    override fun onPause() {
        super.onPause()
        removeScrollListener(scrollListener)
    }

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)

            // sticky header
            binding.storeshopSticky.apply {
                adapter = storeShopCateAdapter
            }
        }

        // holder click events
        EventBus.viewHolderEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let { event ->
                if (event.tabType == TabType.STORE_SHOP) {
                    when (event.eventType) {
                        ViewHolderEventType.CATEGORY_SCROLL -> {
                            val pos = event.content as Int
                            scrollToX(pos)
                            scrollToY(pos)
                        }
                        ViewHolderEventType.GRID_CLICK -> {
                            viewModel.updateGrid()
                        }
                        ViewHolderEventType.SORT_CLICK -> {
                            viewModel.updateSort(event.content as String)
                        }
                        ViewHolderEventType.STORE_CLICK -> {
                            viewModel.updateStore(event.content as StoreShopResponse.StorePick)
                        }
                    }

                }
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.list.layoutManager as? LinearLayoutManager ?: return

            val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()

            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.StoreShopCateTabData }
            binding.storeshopSticky.apply {
                visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (isVisible) {
                    scrollToX(firstVisiblePos)
                }
            }
        }
    }

    private fun scrollToX(position: Int) {
        val selected = (position - 17) / 6
        storeShopCateAdapter.tabSelector(selected)

        val halfScreen = getScreenWidthToPx() / 2
        val halfHolder = getScreenWidthToPx() / 10
        (binding.storeshopSticky.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(selected, halfScreen - halfHolder)
    }

    private fun scrollToY(pos: Int) {
        val posList = mutableListOf<Int>()
        moduleAdapter.value.forEachIndexed { index, moduleData ->
            if (moduleData is ModuleData.StoreShopCateTitleData) {
                posList.add(index)
            }
        }

        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(posList[pos], 0)
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