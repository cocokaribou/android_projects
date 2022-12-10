package com.example.elandmall_kotlin.ui.main.tabs.plandetail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.ui.main.tabs.DialogType
import com.example.elandmall_kotlin.util.Logger

class PlanDetailModuleFragment : BaseModuleFragment() {
    override val viewModel: PlanDetailViewModel by viewModels()

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
            initSticky()
        }
    }

    private fun initSticky() = with(binding) {
        plandetailStickySort.apply {
            sort.text = viewModel.tabData[1].dispCtgNm
            sort.setOnClickListener {
                BottomSheetFragment(
                    type = DialogType.PLAN_DETAIL_TAB,
                    list = viewModel.tabData
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

        plandetailStickyTitle.apply {
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

            binding.plandetailStickySort.apply {
                root.visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (isVisible) {
                    // do event
                }
            }
        }
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