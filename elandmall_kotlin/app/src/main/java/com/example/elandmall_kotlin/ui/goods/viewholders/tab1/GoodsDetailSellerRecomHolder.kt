package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailSellerRecomBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailSellerRecomItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI

class GoodsDetailSellerRecomHolder(val binding: ViewGoodsDetailSellerRecomBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { SellerRecomAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<Goods>()
            initUI(dataList)
        }
    }

    private fun initUI(dataList: List<Goods>) = with(binding) {
        list.adapter = mAdapter
        mAdapter.submitList(dataList)

        // flexible UI
        header
            .setPaddingVertical(dp = 13)
            .setDivider(isVisible = false)
    }

    class SellerRecomAdapter : ListAdapter<Goods, SellerRecomHolder>(object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem.goodsNm == newItem.goodsNm
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SellerRecomHolder(
                ViewGoodsDetailSellerRecomItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: SellerRecomHolder, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    class SellerRecomHolder(val binding: ViewGoodsDetailSellerRecomItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(goods: Goods) {
            drawGoodsUI(binding, goods)
        }
    }
}