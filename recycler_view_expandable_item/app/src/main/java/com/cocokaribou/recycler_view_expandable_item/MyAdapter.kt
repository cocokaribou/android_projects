package com.cocokaribou.recycler_view_expandable_item

import android.graphics.Paint
import android.net.Uri
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

class MyAdapter(private val myClickListener: (BestVO.Goods) -> Unit) : ListAdapter<BestVO.Goods, MyAdapter.BestItemHolder>(diffUtil) {
    lateinit var goodsArray: MutableList<BestVO.Goods>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemHolder {
        return BestItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_goods_preview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestItemHolder, position: Int) {
        holder.bind(goodsArray[position])
        holder.itemBinding.root.setOnClickListener{
            myClickListener(goodsArray[position])
        }
    }


    class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemBinding = ItemGoodsPreviewBinding.bind(itemView)
        fun bind(goods: BestVO.Goods) {
            itemBinding.tvBrandName.text = goods.brandNm
            itemBinding.tvGoodsName.text = goods.goodsNm
            if (goods.marketPrice == 0) {
                itemBinding.tvOriginPrice.visibility = View.GONE
            } else {
                itemBinding.tvOriginPrice.visibility = View.VISIBLE
                itemBinding.tvOriginPrice.text = "${goods.marketPrice}원"
                itemBinding.tvOriginPrice.paintFlags =
                    itemBinding.tvSalesRate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            itemBinding.tvSalesPrice.text = "${goods.salePrice}원"
            if (goods.saleRate == 0) {
                itemBinding.tvSalesRate.visibility = View.GONE
            }else{
                itemBinding.tvSalesRate.visibility = View.VISIBLE
                itemBinding.tvSalesRate.text = "${goods.saleRate}%"
            }

            // image
            val imageUrl = goods.imgUrl.substring(2, goods.imgUrl.length)
            Log.e("image url", "$imageUrl")
            Glide.with(itemBinding.ivGoodsImg.context)
                .load(Uri.parse(imageUrl))
                .into(itemBinding.ivGoodsImg)

            itemBinding.root.setOnClickListener {

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