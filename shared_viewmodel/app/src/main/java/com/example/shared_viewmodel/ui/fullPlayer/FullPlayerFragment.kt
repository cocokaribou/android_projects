package com.example.shared_viewmodel.ui.fullPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shared_viewmodel.databinding.FragmentFullplayerBinding
import com.example.shared_viewmodel.databinding.FragmentIntroBinding

class FullPlayerFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentFullplayerBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFullplayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}