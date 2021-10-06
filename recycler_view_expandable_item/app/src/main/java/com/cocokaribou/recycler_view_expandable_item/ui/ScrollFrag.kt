package com.cocokaribou.recycler_view_expandable_item.ui

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

    companion object {
        var itemHeight: Int = 0
    }

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
        initScrollIndicator()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initLinear() {
    }

    private fun initAdapter() {
        binding.recyclerview.adapter = MyAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initScrollIndicator() {

//        val indicatorItem = IndicatorItem.Builder(requireActivity().requireViewById(R.layout.item_indicator))
//        indicatorView.addIndicatorItem(indicatorItem)

        with(binding) {
            indicatorScrollview.bindIndicatorView(indicatorView)

            indicatorView.addIndicatorItem(
                IndicatorItem.Builder(section1)
                    .setItemColor(Color.parseColor("#777777"))
                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
                    .setExpandedSize(itemHeight)
                    .build()
            )
            indicatorView.addIndicatorItem(
                IndicatorItem.Builder(section2)
                    .setItemColor(Color.parseColor("#888888"))
                    .setIndicatorAnimation(IndicatorAnimation.ACCELERATE)
                    .setExpandedSize(itemHeight)
                    .build()
            )

//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section3)
//                    .setItemColor(Color.parseColor("#999999"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section4)
//                    .setItemColor(Color.parseColor("#aaaaaa"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section5)
//                    .setItemColor(Color.parseColor("#bbbbbb"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section6)
//                    .setItemColor(Color.parseColor("#cccccc"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section7)
//                    .setItemColor(Color.parseColor("#dddddd"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section8)
//                    .setItemColor(Color.parseColor("#dedede"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section9)
//                    .setItemColor(Color.parseColor("#eeeeee"))
//                    .setIndicatorAnimation(IndicatorAnimation.BOUNCE)
//                    .setExpandedSize(400)
//                    .build()
//            )
//            indicatorView.addIndicatorItem(
//                IndicatorItem.Builder(section10)
//                    .setItemColor(Color.parseColor("#efefef"))
//                    .setIndicatorAnimation(IndicatorAnimation.ACCELERATE)
//                    .setExpandedSize(400)
//                    .build()
//            )

        }
    }

    class MyAdapter :
        RecyclerView.Adapter<MyAdapter.NumberHolder>() {
        val list =
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
            val holderLayout = NumberHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_scroll, parent, false)
            )
            itemHeight = holderLayout.itemBinding.itemLayout.height
            return holderLayout
        }

        override fun onBindViewHolder(holder: NumberHolder, position: Int) {
            holder.itemBinding.txtArray.text = list[position].toString()
            holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#f0f7f0"))
            itemHeight = holder.itemBinding.itemLayout.height

        }


        class NumberHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemBinding = ItemScrollBinding.bind(itemView)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}