package com.youngin.lunch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youngin.lunch.databinding.ViewStoreItemBinding
import com.youngin.lunch.model.StoreDataList

class StoreImgAdapter : ListAdapter<StoreDataList.StoreData, StoreImgAdapter.StoreImgHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<StoreDataList.StoreData>() {
            override fun areItemsTheSame(oldItem: StoreDataList.StoreData, newItem: StoreDataList.StoreData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoreDataList.StoreData, newItem: StoreDataList.StoreData): Boolean {
                return oldItem.stoImgUrl == newItem.stoImgUrl
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreImgHolder {
        return StoreImgHolder(ViewStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StoreImgHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class StoreImgHolder(private val itemBinding: ViewStoreItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(storeData: StoreDataList.StoreData) {
            Glide.with(itemView).load(storeData.stoImgUrl).centerCrop().into(itemBinding.storeImg)
        }
    }
}