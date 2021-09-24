package com.cocokaribou.recycler_view_expandable_item

import android.graphics.Paint
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cocokaribou.recycler_view_expandable_item.databinding.ItemGoodsPreviewBinding

class MyAdapter :
    ListAdapter<BestVO.Goods, MyAdapter.BestItemHolder>(diffUtil) {
    lateinit var goodsArray: MutableList<BestVO.Goods>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemHolder {
        return BestItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_goods_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestItemHolder, position: Int) {
        holder.bind(goodsArray[position])
    }


    class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemBinding = ItemGoodsPreviewBinding.bind(itemView)
        private var previousTime = SystemClock.elapsedRealtime()
        fun bind(goods: BestVO.Goods) {
            itemBinding.run {
                tvBrandName.text = goods.brandNm
                tvGoodsName.text = goods.goodsNm
                if (goods.marketPrice == 0) {
                    tvOriginPrice.visibility = View.GONE
                } else {
                    tvOriginPrice.visibility = View.VISIBLE
                    tvOriginPrice.text = "${goods.marketPrice}원"
                    tvOriginPrice.paintFlags = tvSalesRate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                tvSalesPrice.text = "${goods.salePrice}원"
                if (goods.saleRate == 0) {
                    tvSalesRate.visibility = View.GONE
                } else {
                    tvSalesRate.visibility = View.VISIBLE
                    tvSalesRate.text = "${goods.saleRate}%"
                }

                // image
                var imageUrl = goods.imgUrl.substring(2, goods.imgUrl.length)
                imageUrl = "http://$imageUrl"
                Glide.with(ivGoodsImg.context)
                    .load(imageUrl)
                    .into(ivGoodsImg)

                root.setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= itemTransformationLayout.duration) {
                        DetailActivity.startActivity(root.context, itemTransformationLayout, goods)
                        previousTime = now
                    }
                }
            }
        }
    }

    override fun submitList(list: MutableList<BestVO.Goods>?) {
        super.submitList(list)
        if (!list.isNullOrEmpty()) {
            goodsArray = list
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BestVO.Goods>() {
            override fun areItemsTheSame(
                oldItem: BestVO.Goods,
                newItem: BestVO.Goods
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItems: BestVO.Goods,
                newItems: BestVO.Goods
            ): Boolean {
                return oldItems.goodsNo == newItems.goodsNo
            }
        }
    }
}