package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopCategoryAdapter
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopCategoryAdapter.Companion.storeShopCateAdapter
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getScreenWidthToPx
import kotlin.math.round

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
                Logger.v("✅ refresh")
                binding.swipeRefresh.isRefreshing = false
                requestRefresh()
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

    fun scrollToX(position: Int) {
        val double = position.toDouble() / storeShopCateAdapter.cateCount.toDouble()
        val selected = (round(double) - 2).toInt()
        storeShopCateAdapter.tabSelector(selected)

        val halfScreen = getScreenWidthToPx() / 2
        val halfHolder = getScreenWidthToPx() / 10
        (binding.sticky.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(selected, halfScreen - halfHolder)
    }

    fun scrollToY(pos: Int) {
        val posList = mutableListOf<Int>()
        moduleAdapter.value.forEachIndexed { index, moduleData ->
            if (moduleData is ModuleData.StoreShopCateNameData) {
                posList.add(index)
            }
        }

        val stickyHeight = binding.sticky.height

        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(posList[pos], stickyHeight)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.list.layoutManager as? LinearLayoutManager ?: return

            val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()

            // store shop fragment sticky
            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.StoreShopCateTabData }
            binding.sticky.apply {
                visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (isVisible) {
                    if (moduleAdapter.value[firstVisiblePos] is ModuleData.StoreShopCateNameData) {
                        scrollToX(firstVisiblePos)
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