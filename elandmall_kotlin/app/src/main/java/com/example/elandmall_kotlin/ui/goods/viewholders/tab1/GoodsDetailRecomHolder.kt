package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailItemBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailRecomBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class GoodsDetailRecomHolder(private val binding: ViewGoodsDetailRecomBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { RecomAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<Goods>()
            initUI(dataList)
        }
    }

    private fun initUI(dataList: List<Goods>) = with(binding) {
        list.adapter = mAdapter
        mAdapter.submitList(dataList)

        header
            .setPaddingVertical(dp = 13)
            .setDivider(isVisible = false)

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(HorizontalSpacingItemDecoration(10.dpToPx(), 10.dpToPx(), true))
        }
    }

    class RecomAdapter : ListAdapter<Goods, RecomHolder>(object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem.goodsNm == newItem.goodsNm
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecomHolder(
            ViewGoodsDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun onBindViewHolder(holder: RecomHolder, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    class RecomHolder(val binding: ViewGoodsDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods) {
            GoodsUtil.drawGoodsUI(binding, data)

            binding.brandName.visibility = if (data.brandNm.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        }
    }
}