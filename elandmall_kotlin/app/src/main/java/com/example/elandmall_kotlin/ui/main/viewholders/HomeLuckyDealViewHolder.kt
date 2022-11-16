package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeLuckyDealBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.getSpannedSizeText

// TODO goods 데이터용 UI 함수들 하나로 묶기
// TODO imageUrl 일괄검사 (도메인 여부 체크)
class HomeLuckyDealViewHolder(private val binding: ViewHomeLuckyDealBinding): BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeLuckyDealData)?.let {
            initView(it.homeLuckyDealData)
        }
    }

    private fun initView(data: Goods) = with(binding){
        Glide.with(itemView.context)
            .load(data.imageUrl)
            .into(goodsImg)

        goodsBrand.text = data.brandNm

        goodsName.text = data.goodsNm

        if (!data.flagImgUrl.isNullOrEmpty()) {
            flag.visibility = View.VISIBLE
            Glide.with(itemView.context)
                .load(data.flagImgUrl)
                .into(flag)
        } else {
            flag.visibility = View.GONE
        }

        val before = getPrice(data.marketPrice ?: 0, data.custSalePrice ?: 0)
        if (before == 0) {
            priceBefore.visibility = View.GONE
        } else {
            val beforeWon = "${before}원"
            priceBefore.text = getSpannedSizeText(beforeWon, before.toString(), 12)
        }

        if (data.saleRate == 0) {
            saleRate.visibility = View.GONE
        } else {
            saleRate.text = data.saleRate.toString()
        }

        val price = "${data.custSalePrice}원"
        priceAfter.text = getSpannedSizeText(price, data.custSalePrice.toString(), sizeDp = 18, bold = true)


        if (data.iconView.isNullOrEmpty()) {
            tag.visibility = View.GONE
        } else {
            val list = data.iconView.split(",").map { it.trim() }
            list.forEach {
                val tv = TextView(itemView.context, null, 0, R.style.home_tag_style).apply {
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

    }

    private fun getPrice(market: Int, cust: Int): Int {
        return if (market > cust) market
        else 0
    }
}