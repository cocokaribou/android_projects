package com.example.sampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapp.databinding.ItemBiasedBinding
import com.example.sampleapp.databinding.ItemLoadingBinding
import com.example.sampleapp.databinding.ItemNormalBinding
import java.util.*

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<String>()

    private var gridCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == dataList.size-1){
            val itemLoadingBinding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingHolder(itemLoadingBinding)
        }
        val itemNormalBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormalHolder(itemNormalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NormalHolder -> {
                holder.bind(dataList, position)
            }
            is LoadingHolder -> {

            }
        }
    }


    override fun getItemViewType(position: Int): Int { // 여기서 뷰타입 정한다
        return position
    }

    fun submitList(mList: MutableList<String>) {
        dataList = mList
    }

    override fun getItemCount(): Int {
        return dataList.size //여기가 0을 뱉고 있었군
    }

    fun setGridCount(count: Int) {
        gridCount = count
        notifyDataSetChanged()
    }

    class NormalHolder(private var itemBinding: ItemNormalBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }
        }
    }

    class LoadingHolder(private val itemBinding: ItemLoadingBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    }

}