package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.example.elandmall_kotlin.databinding.ViewStoreShopRecommendBinding
import com.example.elandmall_kotlin.databinding.ViewStoreShopRecommendItemBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopRecommendViewHolder(private val binding: ViewStoreShopRecommendBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { RecommendAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopRecommendData)?.let {
            val list = it.storeShopRecommendData.toMutableList()
            list.add(StoreShopResponse.RecommendStore("$mainDomain/app/initRecommendStoreLayer.action"))
            initUI(list)
        }
    }

    private fun initUI(data: List<StoreShopResponse.RecommendStore>) = with(binding) {
        mAdapter.submitList(data)
        list.adapter = mAdapter
    }

    inner class RecommendAdapter : ListAdapter<StoreShopResponse.RecommendStore, RecommendAdapter.RecommendHolder>(diff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
            return RecommendHolder(
                ViewStoreShopRecommendItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
            holder.onBind()
        }

        inner class RecommendHolder(private val binding: ViewStoreShopRecommendItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val currentItem = currentList[adapterPosition]

                Glide.with(root.context)
                    .load("http:" + currentItem.imageUrl)
                    .into(storeImg)

                storeName.text = currentItem.storeNm

                currentItem.linkUrl?.let { link ->
                    root.setOnClickListener {
                        val linkUrl = if (link.startsWith("http")) {
                            currentItem.linkUrl
                        } else {
                            mainDomain + currentItem.linkUrl
                        }

                        EventBus.fire(LinkEvent(linkUrl))
                    }
                }

                if (currentItem.storeNm.isNullOrEmpty()) {
                    // last viewholder
                    moreLay.visibility = View.VISIBLE
                    recommendLay.visibility = View.GONE
                } else {
                    // else
                    moreLay.visibility = View.GONE
                    recommendLay.visibility = View.VISIBLE
                }
            }
        }

    }

    val diff = object : DiffUtil.ItemCallback<StoreShopResponse.RecommendStore>() {
        override fun areItemsTheSame(oldItem: StoreShopResponse.RecommendStore, newItem: StoreShopResponse.RecommendStore): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StoreShopResponse.RecommendStore, newItem: StoreShopResponse.RecommendStore): Boolean {
            return oldItem.storeNm == newItem.storeNm
        }
    }

}