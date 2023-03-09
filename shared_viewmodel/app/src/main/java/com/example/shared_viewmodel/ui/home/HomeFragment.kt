package com.example.shared_viewmodel.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.shared_viewmodel.ui.MainActivity
import com.example.shared_viewmodel.databinding.FragmentHomeBinding
import com.example.shared_viewmodel.ui.CommonModuleRecyclerViewAdapter
import com.example.shared_viewmodel.ui.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentHomeBinding? = null

    private val activityViewModel: MainViewModel by activityViewModels()

    private var tabLayoutMediator: TabLayoutMediator? = null

    private val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.apply {
            adapter = CommonModuleRecyclerViewAdapter(this@HomeFragment)
            offscreenPageLimit = 10
        }

        tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewpager) { tabs, pos -> tabs.text = pos.toString() }
        tabLayoutMediator!!.attach()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? MainActivity)?.onBackPressedDispatcher?.addCallback(callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewpager.adapter = null
        tabLayoutMediator = null
        _binding = null
    }
}