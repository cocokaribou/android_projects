package com.example.elandmall_kotlin.ui.goods.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewGoodsTopImageBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsTopImageItemBinding
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.CustomTabUtil.draw
import com.example.elandmall_kotlin.util.Logger
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GoodsTopImageHolder(val binding: ViewGoodsTopImageBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { TopImageAdapter() }

    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<GoodsResponse.Data.TopImage>()
            initUI(dataList)
        }
    }

    fun initUI(data: List<GoodsResponse.Data.TopImage>) = with(binding) {
        list.adapter = mAdapter

        mAdapter.submitList(data)

        TabLayoutMediator(tabs, list) { tabs, list ->

        }.attach()
    }

    class TopImageAdapter :
        ListAdapter<GoodsResponse.Data.TopImage, TopImageHolder>(object : DiffUtil.ItemCallback<GoodsResponse.Data.TopImage>() {
            override fun areItemsTheSame(oldItem: GoodsResponse.Data.TopImage, newItem: GoodsResponse.Data.TopImage): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: GoodsResponse.Data.TopImage, newItem: GoodsResponse.Data.TopImage): Boolean =
                oldItem.imgUrl == newItem.imgUrl

        }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            TopImageHolder(
                ViewGoodsTopImageItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: TopImageHolder, position: Int) {
            holder.onBind(currentList[position])
        }

    }

    class TopImageHolder(val binding: ViewGoodsTopImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: GoodsResponse.Data.TopImage) {
            Glide.with(binding.root.context)
                .load(data.imgUrl)
                .into(binding.image)
        }
    }
}