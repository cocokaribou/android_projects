package com.example.elandmall_kotlin.ui.main.viewholders

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeStoreShopBannerItemBinding
import com.example.elandmall_kotlin.databinding.ViewHomeStoreShopBinding
import com.example.elandmall_kotlin.databinding.ViewHomeStoreShopItemBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.goodsDiff
import com.google.android.material.tabs.TabLayoutMediator

class HomeStoreShopViewHolder(private val binding: ViewHomeStoreShopBinding) : BaseViewHolder(binding.root) {
    private val mBannerAdapter by lazy { BannerAdapter() }
    private val mListAdapter by lazy { StoreListAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeStoreShopData)?.let {
            initView(it.homeStoreShopData)
        }
    }

    private fun initView(data: HomeResponse.HomeOfflineShop) = with(binding) {
        storeBannerList.adapter = mBannerAdapter
        mBannerAdapter.submitList(data.homeOfflineShopBanner)
        TabLayoutMediator(storeBannerIndicator, storeBannerList) { _, _ -> }.attach()

        mListAdapter.submitList(data.homeOfflineShopList)
        storeList.adapter = mListAdapter
    }

    inner class BannerAdapter : ListAdapter<Banner, BannerAdapter.BannerViewHolder>(object : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.linkUrl == newItem.linkUrl
        }
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
            return BannerViewHolder(
                ViewHomeStoreShopBannerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
            holder.onBind()
        }


        inner class BannerViewHolder(private val binding: ViewHomeStoreShopBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() {
                val data = currentList[adapterPosition]
                Glide.with(binding.root.context)
                    .load(data.imageUrl)
                    .into(binding.bannerImg)

                binding.root.setOnClickListener {
                    EventBus.fire(LinkEvent(data.linkUrl))
                }
            }
        }
    }

    inner class StoreListAdapter : ListAdapter<Goods, StoreListAdapter.StoreItemViewHolder>(goodsDiff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreItemViewHolder {
            return StoreItemViewHolder(
                ViewHomeStoreShopItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: StoreItemViewHolder, position: Int) {
            holder.onBind()
        }

        inner class StoreItemViewHolder(private val binding: ViewHomeStoreShopItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = currentList[adapterPosition]
                drawGoodsUI(binding, data)

                if (data.saleRate != 0) {
                    priceBefore.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }
    }
}