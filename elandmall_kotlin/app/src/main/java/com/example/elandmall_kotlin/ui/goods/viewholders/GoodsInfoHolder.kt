package com.example.elandmall_kotlin.ui.goods.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsInfoBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.priceToString
import com.example.elandmall_kotlin.util.GoodsUtil.removeClickListener
import com.example.elandmall_kotlin.util.Logger

class GoodsInfoHolder(val binding: ViewGoodsInfoBinding) : GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? Map<*, *>)?.let { data ->
            val share = (data["share"] as? GoodsResponse.Data.Share)
            val info = (data["info"] as? Goods)
            initUI(share, info)
        }
    }

    private fun initUI(shareData: GoodsResponse.Data.Share?, info: Goods?) = with(binding) {
        brandName.text = info?.brandNm ?: ""

        Glide.with(root.context)
            .load(info?.brandImgUrl)
            .into(logo)

        share.setOnClickListener {
            Logger.v("${shareData?.shareUrl}")
        }

        info ?: return
        drawGoodsUI(binding, info).removeClickListener()


        priceFinal.text = "${info.finalPrice?.priceToString()}원"

        dropdown.setOnClickListener {
            if (expanded.visibility == View.GONE) {
                expanded.visibility = View.VISIBLE
                dropdown.setImageResource(R.drawable.ic_btn_drop_up)
            } else {
                expanded.visibility = View.GONE
                dropdown.setImageResource(R.drawable.ic_btn_drop_down)
            }
        }

        priceBefore2.text = "${info.marketPrice?.priceToString()}원"

        if (info.custSalePrice != 0) {
            priceAfter2.text = "- ${info.custSalePrice?.priceToString()}원"
        } else {
            price2.visibility = View.GONE
            priceAfter2.visibility = View.GONE
        }

        if (info.couponSalePrice != 0) {
            priceCouponSale.text = "- ${info.couponSalePrice?.priceToString()}원"
        } else {
            price3.visibility = View.GONE
            priceCouponSale.visibility = View.GONE
        }

        pointRate.text = info.point
    }
}