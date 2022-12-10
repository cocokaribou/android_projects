package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

class StoreShopStickyAdapter : RecyclerView.Adapter<StoreShopStickyAdapter.StickyViewHolder>() {
    companion object {
        val storeShopCateAdapter by lazy { StoreShopStickyAdapter() }
    }

    fun submitList(list: List<StoreShopResponse.CategoryGoods>) {
        currentList = list
    }

    private var currentList = listOf<StoreShopResponse.CategoryGoods>()

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder {
        return StickyViewHolder(
            ViewStoreShopCateTabItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StickyViewHolder, position: Int) {
        holder.onBind()
    }

    var tabSelected = 0

    inner class StickyViewHolder(private val binding: ViewStoreShopCateTabItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() = with(binding) {
            // weight
            root.layoutParams.width = getScreenWidthToPx() / 5

            val currentItem = currentList[adapterPosition]

            // prevent glitching
            Glide.with(itemView.context)
                .load("http:${currentItem.dactiveImgUrl}")
                .override(30.dpToPx(), 30.dpToPx())
                .into(cateImgDefault)

            Glide.with(itemView.context)
                .load("http:${currentItem.activeImgUrl}")
                .override(30.dpToPx(), 30.dpToPx())
                .into(cateImgSelected)


            if (adapterPosition == tabSelected) {
                selectIndicator.isSelected = true
                cateImgSelected.visibility = View.VISIBLE
                cateImgDefault.visibility = View.GONE
            } else {
                selectIndicator.isSelected = false
                currentItem.dactiveImgUrl
                cateImgSelected.visibility = View.GONE
                cateImgDefault.visibility = View.VISIBLE
            }

            cateName.text = currentItem.ctgNm

            root.setOnClickListener {
                EventBus.fire(StoreShopEvent(StoreShopEventType.CATEGORY_SCROLL, adapterPosition))
                tabSelected = adapterPosition

                notifyDataSetChanged()
            }
        }
    }

    fun tabSelector(index: Int) {
        tabSelected = index
        notifyDataSetChanged()
    }
}