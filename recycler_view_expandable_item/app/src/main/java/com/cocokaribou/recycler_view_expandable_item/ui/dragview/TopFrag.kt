package com.cocokaribou.recycler_view_expandable_item.ui.dragview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentFrameTopBinding

class TopFrag: Fragment() {
    lateinit var binding: FragmentFrameTopBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrameTopBinding.inflate(inflater)
        return binding.root
    }
}