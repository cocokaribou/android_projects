package com.example.shared_viewmodel.ui.leftmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shared_viewmodel.databinding.FragmentIntroBinding
import com.example.shared_viewmodel.databinding.FragmentLeftmenuBinding

class LeftMenuFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLeftmenuBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLeftmenuBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}