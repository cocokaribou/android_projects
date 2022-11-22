package com.example.elandmall_kotlin.util

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.model.Goods
import java.text.NumberFormat
import java.util.*

// Goods.kt model
object GoodsUtil {
    fun drawGoodsUI(binding: ViewBinding, data: Goods) {
        binding.root.let {
            // goods image
            try {
                val goodsImg = it.findViewById<ImageView>(R.id.goods_img)
                Glide.with(it.context)
                    .load(data.imageUrl)
                    .into(goodsImg)
            } catch (e: RuntimeException) { }


            // flag
            try {
                val flag = it.findViewById<ImageView>(R.id.flag)
                if (!data.flagImgUrl.isNullOrEmpty()) {
                    flag.visibility = View.VISIBLE

                    Glide.with(it.context)
                        .load(data.flagImgUrl)
                        .into(flag)
                } else {
                    flag.visibility = View.GONE
                }
            } catch (e: RuntimeException) {
            }


            // brand name
            try {
                val brandNm = it.findViewById<TextView>(R.id.brand_name)
                if (data.brandNm.isNullOrEmpty()) {
                    brandNm.visibility = View.GONE
                } else {
                    brandNm.visibility = View.VISIBLE
                    brandNm.text = data.brandNm
                }
            } catch (e: RuntimeException) {
            }


            // goods name
            try {
                val goodsNm = it.findViewById<TextView>(R.id.goods_name)
                goodsNm.text = data.goodsNm?.useNonBreakingSpace()
            } catch (e: RuntimeException) {
            }


            // price before
            try {
                val priceBefore = it.findViewById<TextView>(R.id.price_before)
                val before = getPrice(data.marketPrice ?: 0, data.custSalePrice ?: 0)
                if (before != 0) {
                    priceBefore.visibility = View.VISIBLE
                    priceBefore.text = "${before.priceToString()}원"
                } else if (data.salePrice != null && data.saleRate != 0) {
                    priceBefore.visibility = View.VISIBLE
                    priceBefore.text = "${data.salePrice.priceToString()}원"
                } else {
                    priceBefore.visibility = View.GONE
                }
            } catch (e: RuntimeException) {
            }


            // sale rate
            try {
                val saleRate = it.findViewById<TextView>(R.id.sale_rate)
                if (data.saleRate == 0) {
                    saleRate.visibility = View.GONE
                } else {
                    saleRate.visibility = View.VISIBLE
                    saleRate.text = data.saleRate.toString()
                }
            } catch (e: RuntimeException) {
            }

            try {
                val saleRatePercent = it.findViewById<TextView>(R.id.sale_rate_percent)
                if (data.saleRate == 0) {
                    saleRatePercent.visibility = View.GONE
                } else {
                    saleRatePercent.visibility = View.VISIBLE
                }
            } catch (e: RuntimeException) {
            }


            // price after
            try {
                val priceAfter = it.findViewById<TextView>(R.id.price_after)
                priceAfter.text = (data.custSalePrice ?: 0).priceToString()
            } catch (e: RuntimeException) {
            }


            // quantity
            try {
                val quantity = it.findViewById<TextView>(R.id.quantity)
                data.saleQty?.let {
                    quantity.text = "${data.saleQty}개 구매"
                }
            } catch (e: RuntimeException) {
            }


            // tag
            try {
                val tag = it.findViewById<LinearLayout>(R.id.tag)
                if (data.iconView.isNullOrEmpty()) {
                    tag.visibility = View.GONE
                } else {
                    tag.removeAllViews()
                    tag.visibility = View.VISIBLE
                    val list = data.iconView.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    list.forEach {
                        val tv = TextView(binding.root.context, null, 0, R.style.home_tag_style).apply {
                            text = it
                        }
                        tag.addView(tv)
                        tag.children.forEachIndexed { i, v ->
                            if (i != 0) {
                                val param = v.layoutParams as ViewGroup.MarginLayoutParams
                                param.setMargins(6, 0, 0, 0)
                                tv.requestLayout()
                            }
                        }
                    }
                }
            } catch (e: RuntimeException) {
            }

            // rating
            try {
                val rating = it.findViewById<RatingBar>(R.id.rating)
                if (data.goodsStarPoint != 0 && data.goodsStarPoint != null) {
                    rating.visibility = View.VISIBLE
                    rating.rating = (data.goodsStarPoint / 20.0).toFloat()
                } else {
                    rating.visibility = View.GONE
                }
            } catch (e: RuntimeException) {}

            // review count
            try {
                val reviewCount = it.findViewById<TextView>(R.id.review_count)
                if (data.goodsCommentCount != 0 && data.goodsCommentCount != null) {
                    reviewCount.text = "(${data.goodsCommentCount})"
                }
            } catch (e: RuntimeException) {}
        }
    }

    private fun getPrice(market: Int, cust: Int): Int {
        return if (market > cust) market
        else 0
    }

    fun Int.priceToString(): String {
        return NumberFormat.getNumberInstance(Locale.US).format(this)
    }

    val goodsDiff = object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.goodsNo == newItem.goodsNo
        }
    }
}