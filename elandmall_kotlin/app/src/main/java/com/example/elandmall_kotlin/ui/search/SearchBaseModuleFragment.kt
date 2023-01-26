package com.example.elandmall_kotlin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.databinding.FragmentSearchBaseModuleBinding
import com.example.elandmall_kotlin.model.SearchModule
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseModuleAdapter
import com.example.elandmall_kotlin.util.Logger

abstract class SearchBaseModuleFragment : Fragment() {
    private var _binding: FragmentSearchBaseModuleBinding? = null
    val binding get() = _binding!!

    private val moduleAdapter by lazy { SearchBaseModuleAdapter() }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBaseModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = moduleAdapter
    }

    abstract fun observeData()

    fun setModules(moduleList: MutableList<SearchModule>) {
        moduleAdapter.submitList(moduleList)
    }
}