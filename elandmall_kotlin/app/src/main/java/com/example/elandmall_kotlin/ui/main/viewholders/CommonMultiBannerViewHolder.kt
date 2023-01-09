package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.elandmall_kotlin.databinding.ViewCommonMultiBannerBinding
import com.example.elandmall_kotlin.databinding.ViewCommonMultiBannerItemBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.AdjustHeightImageViewTarget
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class CommonMultiBannerViewHolder(private val binding: ViewCommonMultiBannerBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { MultiBannerAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonMultiBannerData)?.let {
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonMultiBannerData) = with(binding) {
        mAdapter.submitList(data.multiBannerData)
        list.adapter = mAdapter

        (list.layoutManager as GridLayoutManager).spanCount = data.multiBannerData.size
        if (data.isDividerVisible) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.GONE
        }
    }

    inner class MultiBannerAdapter : ListAdapter<Banner, MultiBannerAdapter.MultiBannerHolder>(object : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean = oldItem.imageUrl == newItem.imageUrl
    }) {

        inner class MultiBannerHolder(private val binding: ViewCommonMultiBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val currentItem = currentList[adapterPosition]

                Glide.with(root.context)
                    .load(currentItem.imageUrl)
                    .override(getScreenWidthToPx(), 0)
                    .into(multiBannerImg)

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(currentItem.linkUrl))
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiBannerHolder {
            return MultiBannerHolder(
                ViewCommonMultiBannerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MultiBannerHolder, position: Int) {
            holder.onBind()
        }
    }
}