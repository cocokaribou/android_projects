package com.example.elandmall_kotlin.util

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.model.Goods
import java.text.NumberFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

// Goods.kt model
object GoodsUtil {
    lateinit var binding: ViewBinding
    fun drawGoodsUI(mBinding: ViewBinding, data: Goods) {
        binding = mBinding
        binding.root.let {
            // goods image
            try {
                val goodsImg = it.findViewById<ImageView>(R.id.goods_img)
                Glide.with(it.context)
                    .load(data.imageUrl)
                    .into(goodsImg)
            } catch (e: RuntimeException) {
            }


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
                    priceBefore.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else if (data.salePrice != null && data.saleRate != 0) {
                    priceBefore.visibility = View.VISIBLE
                    priceBefore.text = "${data.salePrice.priceToString()}원"
                    priceBefore.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    priceBefore.visibility = View.GONE
                }
            } catch (e: RuntimeException) {
            }


            // sale rate
            try {
                val saleRate = it.findViewById<TextView>(R.id.sale_rate)
                if (data.saleRate == 0 || data.saleRate == null) {
                    saleRate.visibility = View.GONE
                } else {
                    saleRate.visibility = View.VISIBLE
                    saleRate.text = data.saleRate.toString()
                }
            } catch (e: RuntimeException) {
            }

            try {
                val saleRatePercent = it.findViewById<TextView>(R.id.sale_rate_percent)
                if (data.saleRate == 0 || data.saleRate == null) {
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

                data.saleQty?.let { it ->
                    val format = if (it >= 10000) {
                        "${it / 10000}만+"
                    } else {
                        it.priceToString()
                    }
                    quantity.text = "${format}개 구매"
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
                        val tv = TextView(binding.root.context, null, 0).apply {
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
                    rating.rating = (data.goodsStarPoint.toFloat() / 20.0).toFloat()
                } else {
                    rating.visibility = View.GONE
                }
            } catch (e: RuntimeException) {
            }

            // review count
            try {
                val reviewCount = it.findViewById<TextView>(R.id.review_count)
                if (data.goodsCommentCount != 0 && data.goodsCommentCount != null) {
                    reviewCount.text = "(${data.goodsCommentCount})"
                } else {
                    reviewCount.text = ""
                }
            } catch (e: RuntimeException) {
            }

            // wish
            try {
                val wish = it.findViewById<ImageView>(R.id.wish)
                wish.setOnClickListener {
                    if (!it.isSelected) Toast.makeText(it.context, "찜!", Toast.LENGTH_SHORT).show()
                    it.isSelected = !it.isSelected
                }
            } catch (e: RuntimeException) {
            }

            // goods link


            // goods rank (best tab)
            try {
                val rank = it.findViewById<TextView>(R.id.rank)
                if (data.rank == 0 || data.rank > 20) {
                    rank.visibility = View.GONE
                } else {
                    rank.visibility = View.VISIBLE

                    val rankTxt = if (data.rank < 10) "0${data.rank}" else data.rank.toString()
                    rank.text = rankTxt

                    val background = if (data.rank in 1..3) R.drawable.background_rank_high else R.drawable.background_rank_default
                    rank.setBackgroundResource(background)

                }
            } catch (e: RuntimeException) {
            }
        }
    }

    fun Unit.tagUIType(type: String) {
        try {
            val tag = binding.root.findViewById<LinearLayout>(R.id.tag)
            when (type) {
                "home" -> {
                    tag.children.forEach {
                        with(it as TextView) {
                            setBackgroundResource(R.drawable.tag_bg)
                            setTextAppearance(R.style.home_tag_style)
                            setPadding(7.dpToPx(), (4.5).toFloat().dpToPx(), 7.dpToPx(), (4.5).toFloat().dpToPx())
                        }
                    }
                }
                "storeshop" -> {
                    tag.children.forEach {
                        with(it as TextView) {
                            setTextAppearance(R.style.store_tag_style)
                            setPadding(0, 0, 7.dpToPx(), 0)
                        }
                    }
                }
                else -> {}
            }

        } catch (e: RuntimeException) {
        }
    }

    private fun getPrice(market: Int, cust: Int): Int {
        return if (market > cust) market
        else 0
    }

    private fun Int.priceToString(): String {
        return NumberFormat.getNumberInstance(Locale.US).format(this)
    }

    val goodsDiff = object : DiffUtil.ItemCallback<Goods>() {
        override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
            return oldItem.goodsNm == newItem.goodsNm
        }
    }
}