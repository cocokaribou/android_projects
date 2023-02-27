package com.example.shared_viewmodel.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shared_viewmodel.ui.MainActivity
import com.example.shared_viewmodel.databinding.FragmentHomeBinding
import com.example.shared_viewmodel.ui.CommonModuleRecyclerViewAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment() {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()

    private val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.apply {
            adapter = CommonModuleRecyclerViewAdapter(this@HomeFragment)
        }

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, pos ->
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? MainActivity)?.onBackPressedDispatcher?.addCallback(callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}