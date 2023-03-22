package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailSellerPopularBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailSellerPopularItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GridSideSpacingItemDecoration
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class GoodsDetailSellerPopularHolder(val binding: ViewGoodsDetailSellerPopularBinding) : GoodsBaseViewHolder(binding.root) {

    private val mAdapter by lazy { PopularAdapter() }
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

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSideSpacingItemDecoration(3, 8.dpToPx()))
        }
    }

    class PopularAdapter : ListAdapter<Goods, PopularHolder>(object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem.goodsNm == newItem.goodsNm

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PopularHolder(
                ViewGoodsDetailSellerPopularItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: PopularHolder, position: Int) {
            holder.onBind(currentList[position])
        }

    }

    class PopularHolder(val binding: ViewGoodsDetailSellerPopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(goods: Goods) {
            drawGoodsUI(binding, goods)
        }
    }
}