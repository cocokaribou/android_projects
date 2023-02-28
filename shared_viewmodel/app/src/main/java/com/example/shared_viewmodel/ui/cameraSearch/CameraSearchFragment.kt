package com.example.shared_viewmodel.ui.cameraSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shared_viewmodel.databinding.FragmentCamerasearchBinding
import com.example.shared_viewmodel.databinding.FragmentIntroBinding

class CameraSearchFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentCamerasearchBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCamerasearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}