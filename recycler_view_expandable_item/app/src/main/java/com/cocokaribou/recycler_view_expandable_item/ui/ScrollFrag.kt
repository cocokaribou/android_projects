package com.cocokaribou.recycler_view_expandable_item.ui

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocokaribou.recycler_view_expandable_item.R
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentScrollBinding
import com.cocokaribou.recycler_view_expandable_item.databinding.ItemScrollBinding
import com.skydoves.indicatorscrollview.*
import java.util.*

class ScrollFrag : Fragment() {
    lateinit var binding: FragmentScrollBinding
    val numList =
        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScrollBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLinear()
        initAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLinear() {
    }

    private fun initAdapter() {
        binding.recyclerview.adapter = MyAdapter(numList)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    fun initScrollIndicator(height: Int) {

        for (i in 0 until numList.size) {
            val section1 = LinearLayout(requireContext())
            with(section1) {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    height
                )
                orientation = LinearLayout.HORIZONTAL
            }
            binding.sectionHolder.addView(section1)

            with(binding) {
                indicatorScrollview.bindIndicatorView(indicatorView)

                indicatorView.addIndicatorItem(
                    IndicatorItem.Builder(section1)
                        .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
                        .setItemColor(Color.parseColor("#454545"))
                        .build()
                )
            }
        }

    }

    inner class MyAdapter(numList: MutableList<Int>) :
        RecyclerView.Adapter<MyAdapter.NumberHolder>() {
        val list = numList
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
            val holderLayout = NumberHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_scroll, parent, false)
            )

            return holderLayout
        }

        override fun onBindViewHolder(holder: NumberHolder, position: Int) {
            holder.itemBinding.txtArray.text = list[position].toString()
            holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#c7c7c7"))
            holder.itemBinding.itemLayout.measure(
                View.MeasureSpec.makeMeasureSpec(
                    View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.EXACTLY
                ),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            val height = holder.itemBinding.itemLayout.measuredHeight
            val marginBottom = holder.itemBinding.itemLayout.marginBottom
            initScrollIndicator(height + marginBottom)
//            height = holder.itemBinding.itemLayout.measuredHeight
        }


        inner class NumberHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemBinding = ItemScrollBinding.bind(itemView)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}