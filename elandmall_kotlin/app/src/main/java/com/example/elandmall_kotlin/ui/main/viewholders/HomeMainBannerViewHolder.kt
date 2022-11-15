package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeMainBannerBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMainBannerItemBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.AdjustHeightImageViewTarget
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getSpannedBoldText

class HomeMainBannerViewHolder(private val binding: ViewHomeMainBannerBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeMainBannerData?)?.let {
            initView(it.homeBannerData)
        }
    }

    private fun initView(data: List<HomeResponse.HomeMainbanner>) = with(binding) {
        viewpager.apply {
            adapter = MainBannerPagerAdapter(data)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val count = "${position+ 1}/${data.size}"
                    counter.text = getSpannedBoldText(count, count[0].toString())
                }
            })
        }

        counter.text = getSpannedBoldText("1/${data.size}", "1")

        popup.setOnClickListener {
            EventBus.fire(LinkEvent(data[viewpager.currentItem].linkUrl))
        }
    }
}

class MainBannerPagerAdapter(private val list: List<HomeResponse.HomeMainbanner>) : RecyclerView.Adapter<MainBannerPagerAdapter.MainBannerItemViewHolder>() {

    inner class MainBannerItemViewHolder(private val binding: ViewHomeMainBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeResponse.HomeMainbanner) = with(binding) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(AdjustHeightImageViewTarget(bannerImg))

            root.setOnClickListener {
                EventBus.fire(LinkEvent(data.linkUrl))
            }
        }
    }

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
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}