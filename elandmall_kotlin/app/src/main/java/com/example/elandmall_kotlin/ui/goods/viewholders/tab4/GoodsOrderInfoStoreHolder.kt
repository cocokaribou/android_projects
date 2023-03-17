package com.example.elandmall_kotlin.ui.goods.viewholders.tab4

import com.example.elandmall_kotlin.databinding.ViewGoodsOrderInfoStoreBinding
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder

class GoodsOrderInfoStoreHolder(val binding: ViewGoodsOrderInfoStoreBinding) : GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? GoodsResponse.Data.OrderInfo)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: GoodsResponse.Data.OrderInfo) = with(binding) {
        row2Text1.text = data.businessName ?: ""
        row2Text2.text = data.representative ?: ""
        row2Text3.text = data.businessNumber ?: ""
        row2Text4.text = data.mailOrderNumber ?: ""
        row2Text5.text = data.address ?: ""

        cs.text = data.customerCenter ?: ""
        contactMail.text = data.mailAddress ?: ""
        contactPhone.text = data.callNumber ?: ""
    }
}