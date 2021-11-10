package com.example.sampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.ItemNormalBinding

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Logger.e("생성 $viewType")
        val itemNormalBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormalHolder(itemNormalBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Logger.e("그려줌 $position")
        when (holder) {
            is NormalHolder -> {
                holder.bind(dataList, position)
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
        return 20
//        return dataList.size
    }

    fun notifyAdapter() {
        notifyDataSetChanged()
    }
}