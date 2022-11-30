package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
import com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryItemBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.util.dpToPx

class CategoryAdapter : ListAdapter<StoreShopResponse.CategoryGoods, CategoryAdapter.StickyViewHolder>(diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder {
        // weight
        val view = ViewHomeCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        view.root.layoutParams.width = parent.width / 5
        return StickyViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickyViewHolder, position: Int) {
        holder.onBind()
    }

    var selected = 0
    inner class StickyViewHolder(private val binding: ViewHomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() = with(binding) {
            val currentItem = currentList[adapterPosition]

            val src = if (adapterPosition == selected) {
                currentItem.activeImgUrl
            } else {
                currentItem.dactiveImgUrl
            }

            Glide.with(itemView.context)
                .load("http:$src")
                .override(30.dpToPx(), 30.dpToPx())
                .diskCacheStrategy(ALL)
                .into(cateImg)
            cateName.text = currentItem.ctgNm

            root.setOnClickListener {
                select(adapterPosition)
            }
        }
    }

    // 1. scroll list
    // 2. select tab
    fun select(pos: Int) {
        selected = pos
        notifyDataSetChanged()
    }
}

val diff = object : DiffUtil.ItemCallback<StoreShopResponse.CategoryGoods>() {
    override fun areItemsTheSame(oldItem: StoreShopResponse.CategoryGoods, newItem: StoreShopResponse.CategoryGoods): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: StoreShopResponse.CategoryGoods,
        newItem: StoreShopResponse.CategoryGoods
    ): Boolean {
        return oldItem.ctgNm == newItem.ctgNm
    }
}