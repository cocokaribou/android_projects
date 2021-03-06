package com.example.shared_viewmodel.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.MainActivity
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentHomeBinding
import com.example.shared_viewmodel.ui.MemberSharedViewModel
import com.example.shared_viewmodel.ui.StoreSharedViewModel

class HomeFragment: Fragment() {

    private var binding: FragmentHomeBinding? = null

    private val memberViewModel: MemberSharedViewModel by activityViewModels()
    private val storeSharedViewModel: StoreSharedViewModel by activityViewModels()
    private val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnGoList?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}