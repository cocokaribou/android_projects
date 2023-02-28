package com.example.shared_viewmodel.ui.notify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shared_viewmodel.databinding.FragmentIntroBinding
import com.example.shared_viewmodel.databinding.FragmentNotifyBinding

class NotifyFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentNotifyBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotifyBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}