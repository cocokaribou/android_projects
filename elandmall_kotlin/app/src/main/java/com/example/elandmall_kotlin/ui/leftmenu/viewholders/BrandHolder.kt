package com.example.elandmall_kotlin.ui.leftmenu.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewLnbBrandBinding
import com.example.elandmall_kotlin.databinding.ViewLnbBrandItemBinding
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration

class BrandHolder(val binding: ViewLnbBrandBinding) : LeftMenuBaseViewHolder(binding.root) {
    private val mAdapter by lazy { BrandAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<LeftMenuResponse.NavCatBrand>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<LeftMenuResponse.NavCatBrand>) = with(binding) {
        mAdapter.submitList(data)
        list.adapter = mAdapter

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(3, 15, true))
        }
    }

    inner class BrandAdapter :
        ListAdapter<LeftMenuResponse.NavCatBrand, BrandAdapter.BrandHolder>(object : DiffUtil.ItemCallback<LeftMenuResponse.NavCatBrand>() {
            override fun areItemsTheSame(oldItem: LeftMenuResponse.NavCatBrand, newItem: LeftMenuResponse.NavCatBrand) = oldItem == newItem

            override fun areContentsTheSame(oldItem: LeftMenuResponse.NavCatBrand, newItem: LeftMenuResponse.NavCatBrand) =
                oldItem.brandNm == newItem.brandNm
        }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandHolder =
            BrandHolder(
                ViewLnbBrandItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: BrandHolder, position: Int) = holder.onBind()

        inner class BrandHolder(private val binding: ViewLnbBrandItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]

                Glide.with(root.context)
                    .load("http:" + item.imageUrl)
                    .into(icon)

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(item.linkUrl))
                }
            }
        }
    }

}