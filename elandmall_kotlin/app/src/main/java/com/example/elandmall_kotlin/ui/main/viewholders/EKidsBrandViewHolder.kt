package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.elandmall_kotlin.databinding.ViewEkidsBrandBinding
import com.example.elandmall_kotlin.databinding.ViewEkidsBrandItemBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class EKidsBrandViewHolder(private val binding: ViewEkidsBrandBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { BrandAdapter() }
    private val mDecoration by lazy { HorizontalSpacingItemDecoration(9.dpToPx(), 12.dpToPx(), true) }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsBrandData)?.let {
            initUI(it.brandList)
        }
    }

    private fun initUI(data: List<Banner>) = with(binding) {
        mAdapter.submitList(data)
        list.adapter = mAdapter

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(mDecoration)
        }
    }


    inner class BrandAdapter : ListAdapter<Banner, BrandAdapter.BrandHolder>(object : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean = oldItem.imageUrl == newItem.imageUrl
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandHolder {
            return BrandHolder(
                ViewEkidsBrandItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: BrandHolder, position: Int) {
            holder.onBind()
        }

        inner class BrandHolder(private val binding: ViewEkidsBrandItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val currentItem = currentList[adapterPosition]

                Glide.with(root.context)
                    .load(currentItem.imageUrl)
                    .into(icon)
            }
        }
    }
}