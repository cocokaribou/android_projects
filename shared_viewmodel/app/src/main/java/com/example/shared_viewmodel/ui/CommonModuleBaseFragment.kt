package com.example.shared_viewmodel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shared_viewmodel.databinding.FragmentModuleBinding

class CommonModuleBaseFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentModuleBinding? = null

    var fragmentNo = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentModuleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            fragmentNo = it.getInt("fragment_no")
        }

        binding.count = fragmentNo
        binding.title.text = "fragment $fragmentNo"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}