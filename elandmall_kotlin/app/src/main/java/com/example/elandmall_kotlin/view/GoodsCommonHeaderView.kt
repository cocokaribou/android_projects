package com.example.elandmall_kotlin.ui.goods.viewholders

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsCommonHeaderBinding

@SuppressLint("Recycle", "CustomViewStyleable")
class GoodsCommonHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0,
) : LinearLayout(context, attrs, def) {

    private val binding by lazy { ViewGoodsCommonHeaderBinding.inflate(LayoutInflater.from(this.context)) }

    init {
        addView(binding.root)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.GoodsCommonHeaderView)

            val headerTitle = typedArray.getString(R.styleable.GoodsCommonHeaderView_header_title)
            binding.title.text = headerTitle
        }
    }
}