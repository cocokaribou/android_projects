package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailPopularBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailPopularItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx

class GoodsDetailPopularHolder(val binding: ViewGoodsDetailPopularBinding) : GoodsBaseViewHolder(binding.root) {

    private val mAdapter by lazy { GoodsDetailPopularAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<Goods>()
            initUI(dataList)
        }
    }

    private fun initUI(dataList: List<Goods>) = with(binding) {
        list.adapter = mAdapter
        mAdapter.submitList(dataList)

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(3, 8.dpToPx(), true))
        }
    }

    class GoodsDetailPopularAdapter : ListAdapter<Goods, GoodsDetailPopularHolder>(object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem.goodsNm == newItem.goodsNm

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            GoodsDetailPopularHolder(
                ViewGoodsDetailPopularItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: GoodsDetailPopularHolder, position: Int) {
            holder.onBind(currentList[position])
        }

    }

    class GoodsDetailPopularHolder(val binding: ViewGoodsDetailPopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(goods: Goods) {
            drawGoodsUI(binding, goods)
        }
    }
}