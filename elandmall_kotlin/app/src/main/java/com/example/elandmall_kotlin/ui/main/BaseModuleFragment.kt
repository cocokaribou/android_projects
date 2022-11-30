package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.databinding.ViewCenterTextBinding
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.viewholders.selectedTab
import com.example.elandmall_kotlin.util.DialogUtil.popUpDialog
import com.example.elandmall_kotlin.util.Logger
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseModuleFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    lateinit var tabName: String
    private var url: String = ""

    companion object {
        const val KEY_ITEM_TAB_NAME = "keyItemTabName"
        const val KEY_ITEM_URL = "keyItemUrl"
    }

    private var _binding: FragmentBaseModuleBinding? = null
    val binding get() = _binding!!

    val moduleAdapter by lazy { BaseModuleAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        arguments?.let {
            tabName = it.getString(KEY_ITEM_TAB_NAME)!!
            url = it.getString(KEY_ITEM_URL) ?: ""
        }

        _binding = FragmentBaseModuleBinding.inflate(inflater, container, false)

        observeData()

        // base UI
        with(binding) {
            // refresh
            swipeRefresh.setOnRefreshListener {
                Logger.v("땡겨요")
                requestRefresh()
            }

            viewModel.refreshComplete.observe(requireActivity()) {
                Logger.v("$it 새로고침")
                binding.swipeRefresh.isRefreshing = false
            }

            // recycler view
            list.apply {
                setHasFixedSize(true)
                adapter = moduleAdapter
                addOnScrollListener(scrollListener)
            }
        }

        return binding.root
    }

    private fun requestRefresh() {
        viewModel.requestRefresh()
    }

    abstract fun observeData()

    fun setModules(moduleList: MutableList<ModuleData>) {
        moduleAdapter.value = moduleList
    }

    fun addFooter(moduleList: MutableList<ModuleData>) {}

    open fun setStickyTab(isOn: Boolean) {}

    open fun selectTab(pos: Int) {}

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // store shop fragment
            val firstVisiblePos = (binding.list.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0
            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.StoreShopCateTabData }
            if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                setStickyTab(true)
            } else {
                setStickyTab(false)
            }

            val posList = mutableMapOf<Int, Int>()
            var index = 0
            moduleAdapter.value.forEachIndexed { i, it ->
                if (it is ModuleData.CenterTextData) {
                    posList[index] = i
                    index++
                }
            }

            val lastVisiblePos = (binding.list.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0
            if (binding.sticky.isVisible) {
                for (i in firstVisiblePos .. lastVisiblePos) {
                    if (moduleAdapter.value[i] is ModuleData.CenterTextData) {
                        selectTab(posList.filterValues { it == i }.keys.first())
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}