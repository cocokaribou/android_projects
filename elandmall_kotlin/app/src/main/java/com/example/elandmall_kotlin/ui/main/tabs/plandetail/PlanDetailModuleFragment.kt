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
import com.example.elandmall_kotlin.util.Logger


class PlanDetailModuleFragment : BaseModuleFragment() {
    var sortSelected = 0
    override val viewModel: PlanDetailViewModel by viewModels()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        addScrollListener(scrollListener)
//        Logger.v("plandetail onViewCreated")
//    }

    override fun onResume() {
        super.onResume()
        addScrollListener(scrollListener)
        Logger.v("plandetail onResume")
    }

    override fun onPause() {
        super.onPause()
        removeScrollListener(scrollListener)
        Logger.v("plandetail onPause")
    }

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)

            initSticky()
        }

        viewModel.index.observe(this) {
            Logger.v("옵저버~")
            binding.plandetailSort.root.visibility = View.VISIBLE
            scrollToY(it)
        }
    }

    private fun initSticky() = with(binding) {
        plandetailTitle.apply {
            root.visibility = View.VISIBLE
            title.text = viewModel.planShopName
        }

        plandetailSort.sort.apply {
            text = viewModel.tabList[sortSelected]
            setOnClickListener {
                BottomSheetFragment(
                    sortCallback = {
                        val pos = it as? Int ?: 0
                        scrollToY(pos)
                    },
                    initIndex = sortSelected,
                    list = viewModel.tabList
                ).show(requireActivity().supportFragmentManager, "")
            }
        }

        plandetailSort.grid.apply {
            val imgSource = when (viewModel.mGridNo) {
                0 -> R.drawable.ic_btn_holder_grid
                1 -> R.drawable.ic_btn_holder_linear
                else -> R.drawable.ic_btn_holder_large
            }
            setImageResource(imgSource)
            setOnClickListener {
                viewModel.updateGrid()
            }
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
                    sortSelected = tabIndex
                    binding.plandetailSort.sort.text = viewModel.tabList[tabIndex]
                }
            }
        }
    }

    private fun scrollToY(pos: Int) {
        binding.plandetailSort.sort.text = viewModel.tabList[pos]

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