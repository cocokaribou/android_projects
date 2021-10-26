package com.example.sampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapp.databinding.ItemNormalBinding

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<String>()
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
        mItemViewType = viewType
        when (mItemViewType) {
            VIEWTYPE_BIGHOLDER -> {
                val itemTestBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return NormalHolder(itemTestBinding)
            }
            else -> {
                val itemTestBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return NormalHolder(itemTestBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NormalHolder -> {
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

    fun submitList(mList: MutableList<String>) {
        dataList = mList
    }

    class NormalHolder(private val itemBinding: ItemNormalBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }
        }
    }

}