package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.ui.ModuleData
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

    override fun onResume() {
        super.onResume()
        observeData()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBaseModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            tabName = it.getString(KEY_ITEM_TAB_NAME)!!
            url = it.getString(KEY_ITEM_URL) ?: ""
        }

        // base UI
        with(binding) {
            // refresh
            swipeRefresh.setOnRefreshListener {

                binding.swipeRefresh.isRefreshing = false
                requestRefresh()
            }

            // recycler view
            list.apply {
                adapter = moduleAdapter
            }
        }
    }

    fun addScrollListener(listener: RecyclerView.OnScrollListener) {
        binding.list.addOnScrollListener(listener)
    }

    fun removeScrollListener(listener: RecyclerView.OnScrollListener) {
        binding.list.removeOnScrollListener(listener)
    }

    private fun requestRefresh() {
        viewModel.requestRefresh()
    }

    abstract fun observeData()

    fun setModules(moduleList: MutableList<ModuleData>) {
        moduleAdapter.value = moduleList
    }

    fun addFooter(moduleList: MutableList<ModuleData>) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}