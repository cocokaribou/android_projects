package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.CategoryAdapter.Companion.categoryAdapter
import com.example.elandmall_kotlin.util.Logger

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

    open fun setStickyVisible(isOn: Boolean) {}

    open fun selectTab(position: Int) {}

    fun scrollToY(pos: Int) {
        val posList = mutableListOf<Int>()
        moduleAdapter.value.forEachIndexed { index, moduleData ->
            if (moduleData is ModuleData.StoreShopCateNameData) {
                posList.add(index)
            }
        }

        (binding.list.layoutManager as? LinearLayoutManager)?.scrollToPosition(posList[pos])
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val firstVisiblePos = (binding.list.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

            // store shop fragment sticky
            val tabPos = moduleAdapter.value.indexOfFirst { it is ModuleData.StoreShopCateTabData }
            binding.sticky.apply {
                visibility = if (moduleAdapter.value.count() != -1 && tabPos <= firstVisiblePos) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                if (isVisible) {
                    moduleAdapter.value.forEachIndexed { i, it ->
                        if (it is ModuleData.StoreShopCateNameData && i <= firstVisiblePos) {
//                            selectTab(i)
                        }
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