package com.cocokaribou.recycler_view_expandable_item.ui.dragview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentFrameBottomBinding

class BottomFrag:Fragment() {
    lateinit var binding:FragmentFrameBottomBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrameBottomBinding.inflate(inflater)
        return binding.root
    }
}