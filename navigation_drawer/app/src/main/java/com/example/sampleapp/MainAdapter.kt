package com.example.sampleapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.ItemTest2Binding
import com.example.sampleapp.databinding.ItemTestBinding

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = listOf<Int>()
    private var mItemViewType = 0

    companion object {
        val VIEWTYPE_BIGHOLDER = 0
        val VIEWTYPE_GRID2HOLDER = VIEWTYPE_BIGHOLDER + 1
        val VIEWTYPE_GRID4HOLDER = VIEWTYPE_GRID2HOLDER + 1
    }

    fun setItemViewType(viewType: Int) {
        mItemViewType = viewType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (mItemViewType) {
            VIEWTYPE_BIGHOLDER -> {
                val itemTestBinding = ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TestHolder(itemTestBinding)
            }
            VIEWTYPE_GRID2HOLDER -> {
                val itemTest2Binding = ItemTest2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TestHolder2(itemTest2Binding)
            }
            else -> {
                val itemTestBinding = ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TestHolder(itemTestBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TestHolder -> {
                holder.bind(dataList, position)
            }
            is TestHolder2 -> {
                holder.bind(dataList, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun getItemViewType(position: Int): Int {
        return mItemViewType
    }

    fun submitList(mList: List<Int>) {
        dataList = mList
    }

    fun willItBeCalled() {

    }

    class TestHolder(private val itemBinding: ItemTestBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: List<Int>, position: Int) {
            itemBinding.text.text = dataList[position].toString()
        }
    }

    class TestHolder2(private val itemBinding: ItemTest2Binding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: List<Int>, position: Int) {
            itemBinding.text.text = dataList[position].toString()
        }
    }


    class BigHolder {}

    class BiasedHolder {}

    class Grid2Holder {}

    class Grid4Holder {}

}