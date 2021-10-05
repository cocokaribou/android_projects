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
import com.skydoves.indicatorscrollview.IndicatorItem
import com.skydoves.indicatorscrollview.IndicatorView
import java.util.*

class ScrollFrag : Fragment() {
    lateinit var binding: FragmentScrollBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScrollBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initScrollIndicator()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initAdapter(){
        binding.recyclerview.adapter = MyAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun initScrollIndicator(){
        val indicatorView = IndicatorView.Builder(requireContext())
            .setIndicatorItemPadding(10)
            .setExpandingRatio(0.2f)
            .setExpandingAllItemRatio(1.0f)
            .build()

//        val indicatorItem = IndicatorItem.Builder(requireActivity().requireViewById(R.layout.item_indicator))
//        indicatorView.addIndicatorItem(indicatorItem)

        binding.recylcerScroll.bindIndicatorView(indicatorView)
    }

    class MyAdapter :
        RecyclerView.Adapter<MyAdapter.NumberHolder>() {
        val list =
            mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
            return NumberHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_scroll, parent, false)
            )
        }

        override fun onBindViewHolder(holder: NumberHolder, position: Int) {
            holder.itemBinding.txtArray.text = list[position].toString()
            val rand = Random().nextInt(list.size) - 1
            when (rand) {
                0 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#7A942E"))
                }
                1 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#6B9362"))
                }
                2 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#049372"))
                }
                3 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#006442"))
                }
                4 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#26A65B"))
                }
                5 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#87D37C"))
                }
                6 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#2ABB9B"))
                }
                7 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#4DAF7C"))
                }
                8 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#8DB255"))
                }
                9 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#407A52"))
                }
                10 -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#5B8930"))
                }
                else -> {
                    holder.itemBinding.txtArray.setBackgroundColor(Color.parseColor("#f0f7f0"))
                }
            }
        }


        class NumberHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemBinding = ItemScrollBinding.bind(itemView)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}