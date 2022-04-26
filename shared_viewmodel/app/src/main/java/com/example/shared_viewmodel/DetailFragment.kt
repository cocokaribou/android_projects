package com.example.shared_viewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.databinding.FragmentDetailBinding
import com.example.shared_viewmodel.model.BaseViewModel

class DetailFragment : Fragment() {

    private val sharedViewModel: BaseViewModel by activityViewModels()

    private var binding: FragmentDetailBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            sharedViewModel.stoContent.observe(viewLifecycleOwner) { content ->
                receivedString = content
            }

            tv.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_listFragment)
            }
        }
    }
}