package com.cocokaribou.recycler_view_expandable_item.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentIntroBinding

class IntroFrag: Fragment() {
    lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroBinding.inflate(inflater)
        return binding.root
    }
}