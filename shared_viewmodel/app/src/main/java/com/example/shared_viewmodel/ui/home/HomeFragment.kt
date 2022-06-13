package com.example.shared_viewmodel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentHomeBinding
import com.example.shared_viewmodel.ui.MemberSharedViewModel
import com.example.shared_viewmodel.ui.StoreSharedViewModel

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val memberViewModel: MemberSharedViewModel by activityViewModels()
    private val storeSharedViewModel: StoreSharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
        }
    }
}