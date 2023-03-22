package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailItemBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailPopularBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class GoodsDetailPopularHolder(private val binding: ViewGoodsDetailPopularBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { PopularAdapter() }
    override fun onBind(item: Any?) {
        (item as? GoodsResponse.Data.PopularGoods)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: GoodsResponse.Data.PopularGoods) = with(binding){

        list.adapter = mAdapter
        mAdapter.submitList(data.goodsList)

        val title = "<u>${data.title}</u> 인기상품"

        header
            .setHeaderTitle(Html.fromHtml(title))
            .setPaddingVertical(dp = 13)
            .setDivider(isVisible = false)

        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(HorizontalSpacingItemDecoration(10.dpToPx(), 10.dpToPx(), true))
        }
    }

    class PopularAdapter : ListAdapter<Goods, PopularHolder>(object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean = oldItem.goodsNm == newItem.goodsNm
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PopularHolder(
            ViewGoodsDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun onBindViewHolder(holder: PopularHolder, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    class PopularHolder(val binding: ViewGoodsDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods) {
            drawGoodsUI(binding, data)

            binding.brandName.visibility = if (data.brandNm.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        }
    }
}