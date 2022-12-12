package com.example.elandmall_kotlin.ui.main.tabs.plandetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.ViewHolderEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.ui.main.tabs.DialogType


class PlanDetailModuleFragment : BaseModuleFragment() {
    val bottomSheetListener: (Int) -> Int = { 0 }
    override val viewModel: PlanDetailViewModel by viewModels()

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
            initSticky()
        }

        EventBus.viewHolderEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let { event ->
                if (event.tabType == TabType.PLAN_DETAIL) {
                    when (event.eventType) {
                        ViewHolderEventType.GRID_CLICK -> {
                            viewModel.updateGrid()
                        }
                        ViewHolderEventType.SORT_CLICK -> {
                            val index = event.content as? Int ?: 0
                            binding.plandetailSort.sort.text = viewModel.tabList[index]
                            scrollToY(index)
                        }
                    }
                }
            }
        }

    }

    private fun initSticky() = with(binding) {
        plandetailSort.apply {
            sort.text = viewModel.tabList[0]
            sort.setOnClickListener {
                BottomSheetFragment(
                    type = DialogType.PLAN_DETAIL_TAB,
                    list = viewModel.tabList
                ).show(requireActivity().supportFragmentManager, "")
            }

            val imgSource = when (viewModel.mGridNo) {
                0 -> R.drawable.ic_btn_holder_grid
                1 -> R.drawable.ic_btn_holder_linear
                else -> R.drawable.ic_btn_holder_large
            }
            grid.setImageResource(imgSource)
            grid.setOnClickListener {
                viewModel.updateGrid()
            }
        }

        plandetailTitle.apply {
            root.visibility = View.VISIBLE
            title.text = viewModel.planShopName
        }

    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.list.layoutManager as? LinearLayoutManager ?: return

            val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()
            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.CommonSortData }

            binding.plandetailSort.apply {
                root.visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (firstVisiblePos > 3) {
                    val tabIndex = viewModel.indexList[firstVisiblePos - 3]
                    binding.plandetailSort.sort.text = viewModel.tabList[tabIndex]
                }
            }
        }
    }

    private fun scrollToY(pos: Int) {
        bottomSheetListener.invoke(pos)
        val posList = mutableListOf<Int>()
        moduleAdapter.value.forEachIndexed { index, moduleData ->
            if (moduleData is ModuleData.PlanDetailTabTitleData) {
                posList.add(index)
            }
        }

        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(posList[pos], 0)
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            PlanDetailModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}