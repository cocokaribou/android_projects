package com.example.elandmall_kotlin.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.elandmall_kotlin.databinding.FragmentBaseModuleBinding
import com.example.elandmall_kotlin.databinding.FragmentSearchBaseModuleBinding
import com.example.elandmall_kotlin.ui.main.BaseModuleAdapter

abstract class SearchBaseModuleFragment : Fragment() {
    private var _binding: FragmentSearchBaseModuleBinding? = null
    val binding get() = _binding!!

    val moduleAdapter by lazy {  }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBaseModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    abstract fun observeData()


    inner class SearchModuleAdapter
}