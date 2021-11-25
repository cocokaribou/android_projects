package com.example.fragment_2.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fragment_2.R
import com.example.fragment_2.databinding.FragmentWebBinding

class WebFragment: Fragment(R.layout.fragment_web) {
    lateinit var binding : FragmentWebBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWebBinding.bind(view)
        binding.webview.loadUrl("https://naver.com")

    }
}