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
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopStickyAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx
import kotlin.math.round

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    override fun onResume() {
        super.onResume()
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.list.layoutManager as? LinearLayoutManager ?: return

            val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()
            val lastVisiblePos = layoutManager.findLastVisibleItemPosition()

            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.StoreShopCateTabData }
            binding.sticky.apply {
                visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (isVisible) {
                    scrollToX(firstVisiblePos)
//                    for (i in firstVisiblePos .. lastVisiblePos) {
//                            scrollToX(i)
//                    }
                }
            }
        }
    }

    fun scrollToX(position: Int) {
        if (moduleAdapter.value[position] is ModuleData.StoreShopCateTitleData) {
            Logger.v("✅타이틀!!!!!!! $position")
        } else {
            Logger.v("position $position")
        }
        val double = position.toDouble() / storeShopCateAdapter.cateCount.toDouble()
        val selected = (round(double) - 1).toInt()
        storeShopCateAdapter.tabSelector(selected)

        val halfScreen = getScreenWidthToPx() / 2
        val halfHolder = getScreenWidthToPx() / 10
        (binding.sticky.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(selected, halfScreen - halfHolder)
    }

    fun scrollToY(pos: Int) {
        val posList = mutableListOf<Int>()
        moduleAdapter.value.forEachIndexed { index, moduleData ->
            if (moduleData is ModuleData.StoreShopCateTitleData) {
                posList.add(index)
            }
        }

        val stickyHeight = binding.sticky.height

        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(posList[pos], stickyHeight)
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