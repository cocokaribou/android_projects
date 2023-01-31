package com.example.elandmall_kotlin.ui.leftmenu.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewLnbShopBinding
import com.example.elandmall_kotlin.databinding.ViewLnbShopItemBinding
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class ShopHolder(val binding: ViewLnbShopBinding) : LeftMenuBaseViewHolder(binding.root) {
    private val mAdapter by lazy { ShopAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<LeftMenuResponse.NavCatShop>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<LeftMenuResponse.NavCatShop>) = with(binding) {
        mAdapter.submitList(data)

        list.adapter = mAdapter
        if(list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(5, 12.dpToPx(), true))
        }
    }

    inner class ShopAdapter :
        ListAdapter<LeftMenuResponse.NavCatShop, ShopAdapter.ShopItemHolder>(object : DiffUtil.ItemCallback<LeftMenuResponse.NavCatShop>() {
            override fun areItemsTheSame(oldItem: LeftMenuResponse.NavCatShop, newItem: LeftMenuResponse.NavCatShop): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: LeftMenuResponse.NavCatShop, newItem: LeftMenuResponse.NavCatShop): Boolean =
                oldItem.shopNm == newItem.shopNm
        }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemHolder =
            ShopItemHolder(
                ViewLnbShopItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: ShopItemHolder, position: Int) {
            holder.onBind()
        }

        inner class ShopItemHolder(private val binding: ViewLnbShopItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]
                Glide.with(root.context)
                    .load("http:"+item.imageUrl)
                    .into(icon)

                title.text = item.shopNm

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(item.linkUrl))
                }
            }
        }
    }
}