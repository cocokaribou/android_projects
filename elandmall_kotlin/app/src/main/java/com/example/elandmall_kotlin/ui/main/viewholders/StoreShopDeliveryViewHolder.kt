package com.example.elandmall_kotlin.ui.main.viewholders

import android.content.res.ColorStateList
import android.graphics.Color
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.example.elandmall_kotlin.databinding.ViewStoreShopDeliveryBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData

class StoreShopDeliveryViewHolder(private val binding: ViewStoreShopDeliveryBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopDeliveryData)?.let {
            initUI(it.storeShopDeliveryData)
        }
    }

    private fun initUI(data: StoreShopResponse.BanjjakDeli) = with(binding) {
        address.text = data.addr

        when (data.flag) {
            "F", "P" -> {
                banjjakTag.setBackgroundResource(R.drawable.background_stroke_radius)
                if (data.flag == "F") {
                    banjjakTag.text = "오늘받송가능"
                    banjjakTag.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffd200"))
                    banjjakTag.setTextAppearance(R.style.banjjak_tag_style_on)

                } else {
                    // P
                    banjjakTag.text = "배송지설정"
                    banjjakTag.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#c9000b"))
                    banjjakTag.setTextAppearance(R.style.banjjak_tag_style_default)
                }
            }
            else -> {
                // D
                banjjakTag.setBackgroundResource(R.drawable.background_solid_radius)
                banjjakTag.text = "오늘받송불가"
                banjjakTag.setTextAppearance(R.style.banjjak_tag_style_off)
            }
        }

        root.setOnClickListener {
            EventBus.fire(LinkEvent(mainDomain + data.btnUrl))
        }
    }
}