package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopStickyAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        addScrollListener(scrollListener)
//        Logger.v("storeshop onViewCreated")
//    }

    override fun onResume() {
        super.onResume()
        addScrollListener(scrollListener)
        scrollToX(visibleCatePos)
        Logger.v("storeshop onResume")
    }

    override fun onPause() {
        super.onPause()
        removeScrollListener(scrollListener)
        Logger.v("storeshop onPause")
    }

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)

            initSticky()
        }
    }

    private fun initSticky() {
        binding.storeshopSticky.apply {
            adapter = storeShopCateAdapter.apply {
                itemClickCallback = { pos ->
                    binding.storeshopSticky.visibility = View.VISIBLE
                    scrollToX(pos)
                    scrollToY(pos)
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

    var visibleCatePos = 0
    private fun scrollToX(position: Int) {
        visibleCatePos = position

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