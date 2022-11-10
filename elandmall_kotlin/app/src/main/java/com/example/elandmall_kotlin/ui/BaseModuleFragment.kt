package com.example.elandmall_kotlin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.FragmentTabCommonBinding
import com.example.elandmall_kotlin.ui.main.CommonModulesAdapter

abstract class BaseModuleFragment: Fragment() {

    companion object {
        const val KEY_ITEM_TAB_NAME = "keyItemTabName"
        const val KEY_ITEM_URL = "keyItemUrl"
    }

    private var _binding: FragmentTabCommonBinding? = null
    val binding get() = _binding!!

    val moduleAdapter by lazy { CommonModulesAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTabCommonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        observeData()
    }

    abstract fun initUI()
    abstract fun observeData()

    fun setModules(moduleList: MutableList<ModuleData>) {
        moduleAdapter.value = moduleList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}