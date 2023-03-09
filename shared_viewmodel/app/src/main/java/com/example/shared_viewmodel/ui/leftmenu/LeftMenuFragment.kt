package com.example.shared_viewmodel.ui.leftmenu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentIntroBinding
import com.example.shared_viewmodel.databinding.FragmentLeftmenuBinding
import com.example.shared_viewmodel.ui.webView.WebViewFragment
import com.example.shared_viewmodel.util.Logger

class LeftMenuFragment: Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentLeftmenuBinding? = null

    private lateinit var callback: OnBackPressedCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLeftmenuBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_leftMenu_to_web)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callback.remove()
    }
}