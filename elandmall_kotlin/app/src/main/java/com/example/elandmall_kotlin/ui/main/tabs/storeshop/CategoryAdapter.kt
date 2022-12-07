package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.ALL
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.elandmall_kotlin.databinding.ViewStoreShopCateTabItemBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.StickyViewHolder>() {
    companion object {
        val categoryAdapter by lazy { CategoryAdapter() }
    }
    var currentList = listOf<StoreShopResponse.CategoryGoods>()

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder {
        // weight
        val view = ViewStoreShopCateTabItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StickyViewHolder(view)
    }

    override fun onBindViewHolder(holder: StickyViewHolder, position: Int) {
        holder.onBind()
    }

    var selectedTab = 0

    inner class StickyViewHolder(private val binding: ViewStoreShopCateTabItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() = with(binding) {
            root.layoutParams.width = getScreenWidthToPx() / 5

            val currentItem = currentList[adapterPosition]

            selectIndicator.isSelected = adapterPosition == selectedTab

            val src = if (adapterPosition == selectedTab) {
                currentItem.activeImgUrl
            } else {
                currentItem.dactiveImgUrl
            }

            // prevent glitching
            Glide.with(itemView.context)
                .asBitmap()
                .load("http:$src")
                .override(30.dpToPx(), 30.dpToPx())
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        cateImg.setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            cateName.text = currentItem.ctgNm

            root.setOnClickListener {
                selectIndicator.isSelected = !selectIndicator.isSelected
                tabSelector(adapterPosition)
            }
        }
    }

    fun tabSelector(index: Int) {
        selectedTab = index

        EventBus.fire(StoreShopEvent(StoreShopEventType.CATEGORY_SCROLL, index))
        notifyDataSetChanged()
    }
}