package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeMainBannerItemBinding
import com.example.elandmall_kotlin.databinding.ViewMainBannerBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.AdjustHeightImageViewTarget
import com.example.elandmall_kotlin.util.getSpannedBoldText

class MainBannerViewHolder(private val binding: ViewMainBannerBinding) : BaseViewHolder(binding.root) {
    val mAdapter by lazy { MainBannerPagerAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.MainBannerData?)?.let {
            initView(it.mainBannerData)
        }
    }

    private fun initView(data: List<Banner>) = with(binding) {
        viewpager.apply {
            mAdapter.submitList(data)
            adapter = mAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val count = "${position + 1}/${data.size}"
                    counter.text = getSpannedBoldText(count, count[0].toString())
                }
            })
        }

        counter.text = getSpannedBoldText("1/${data.size}", "1")

        popup.setOnClickListener {
            EventBus.fire(LinkEvent(data[viewpager.currentItem].linkUrl))
        }
    }

    inner class MainBannerPagerAdapter : ListAdapter<Banner, MainBannerPagerAdapter.MainBannerItemViewHolder>(object :
        DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
            oldItem.linkUrl == newItem.linkUrl
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBannerItemViewHolder {
            return MainBannerItemViewHolder(
                ViewHomeMainBannerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MainBannerItemViewHolder, position: Int) {
            holder.onBind()
        }

        inner class MainBannerItemViewHolder(private val binding: ViewHomeMainBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = currentList[adapterPosition]
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .into(AdjustHeightImageViewTarget(bannerImg))

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(data.linkUrl))
                }
            }
        }
    }
}