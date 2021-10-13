package com.cocokaribou.recycler_view_expandable_item.ui.dragview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cocokaribou.recycler_view_expandable_item.R
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentDragviewBinding
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentFrameTopBinding
import com.tuanhav95.drag.DragView

class DragViewFrag : Fragment() {
    lateinit var binding: FragmentDragviewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDragviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtonView()
        binding.dragview.setDragListener(object:DragView.DragListener{
            override fun onChangeState(state: DragView.State) {
            }
            override fun onChangePercent(percent: Float) {
                when(percent){
                    1.0f -> {
                        Toast.makeText(requireContext(), "shrunk", Toast.LENGTH_SHORT).show()
                        val layout = requireActivity().supportFragmentManager.findFragmentById(R.id.frameFirst)
                        (layout as TopFrag).expandedTextview()
                    }
                    0.0f -> {
                        Toast.makeText(requireContext(), "expanded", Toast.LENGTH_SHORT).show()
                        val layout = requireActivity().supportFragmentManager.findFragmentById(R.id.frameFirst)
                        (layout as TopFrag).shrunkTexttview()
                    }
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    fun initButtonView() {
        binding.btn1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frameFirst, TopFrag()).commit()
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frameSecond, BottomFrag()).commit()
        }
        binding.btn2.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frameFirst, TopExoFrag()).commit()
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.frameSecond, BottomFrag()).commit()
        }

    }

}