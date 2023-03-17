package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.ViewGroup
import android.widget.TextView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailTagBinding
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx
import com.google.android.flexbox.FlexboxLayout

class GoodsDetailTagHolder(val binding: ViewGoodsDetailTagBinding) : GoodsBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<GoodsResponse.Data.Tag>()
            initUI(dataList)
        }
    }

    private fun initUI(dataList: List<GoodsResponse.Data.Tag>) = with(binding) {
        if (tag.childCount == 0)
            dataList.forEach { data ->
                val tagView = TextView(root.context, null, 0, R.style.goods_tag).apply {
                    text = data.tagNm
                    setOnClickListener {
                        Logger.v("${data.tagLink}")
                    }
                }
                tag.addView(tagView)

                val param = tagView.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, 0, 5.dpToPx(), 5.dpToPx())
                tagView.requestLayout()
            }
    }
}