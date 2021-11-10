package com.example.sampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapp.databinding.ItemBiasedLeftBinding
import com.example.sampleapp.databinding.ItemBiasedRightBinding
import com.example.sampleapp.databinding.ItemNormalBinding
import java.util.*

class BiasedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != 0 && viewType % 4 == 0) {
            val itemBiasedBinding = ItemBiasedLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BiasedLeftHolder(itemBiasedBinding)
        } else if (viewType != 0 && viewType % 3 == 0){
            val itemBiasedBinding = ItemBiasedRightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BiasedRightHolder(itemBiasedBinding)
        }
        else {
            val itemNormalBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NormalHolder(itemNormalBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BiasedLeftHolder -> {
                holder.bind(dataList, position)
            }
            is BiasedRightHolder -> {
                holder.bind(dataList, position)
            }
            is NormalHolder -> {
                holder.bind(dataList, position)
            }
        }
    }

    fun submitList(mList: MutableList<String>) {
        dataList = mList
    }

    override fun getItemCount(): Int {
        return 20
//        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class BiasedLeftHolder(private var itemBinding: ItemBiasedLeftBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }
        }
    }

    class BiasedRightHolder(private var itemBinding: ItemBiasedRightBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }
        }
    }
}