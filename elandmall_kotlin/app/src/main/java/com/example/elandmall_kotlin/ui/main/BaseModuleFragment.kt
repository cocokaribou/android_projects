package com.example.elandmall_kotlin.ui.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.isNetworkAvailable

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

        with(binding) {
            swipeRefresh.setOnRefreshListener {
                requestRefresh()
            }
            viewModel.isSuccess.observe(requireActivity()) {
                swipeRefresh.isRefreshing = false
                if (!it) {
                    AlertDialog.Builder(requireActivity()).setMessage("인터넷을 연결해주세요.").setPositiveButton("확인") { _, _ -> }.create().show()
                }
            }

            list.apply {
                setHasFixedSize(true)
                adapter = moduleAdapter
            }
        }

        return binding.root
    }

    protected fun requestRefresh() {
        if (isNetworkAvailable(requireContext())) {
            viewModel.requestRefresh()
        }
    }

    abstract fun observeData()

    fun setModules(moduleList: MutableList<ModuleData>) {
        moduleAdapter.value = moduleList
    }

    fun addFooter(moduleList: MutableList<ModuleData>) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}