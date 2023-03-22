package com.example.elandmall_kotlin.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewGoodsCommonHeaderBinding
import com.example.elandmall_kotlin.util.dpToPx

@SuppressLint("Recycle", "CustomViewStyleable")
class GoodsCommonHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0,
) : ConstraintLayout(context, attrs, def) {

    var binding: ViewGoodsCommonHeaderBinding

    init {
        val view = inflate(context, R.layout.view_goods_common_header, this)
        binding = ViewGoodsCommonHeaderBinding.bind(view)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.GoodsCommonHeaderView)

            val headerTitle = typedArray.getString(R.styleable.GoodsCommonHeaderView_header_title) ?: ""
            binding.title.text = headerTitle
        }
    }

    fun setHeaderTitle(value: String): GoodsCommonHeaderView {
        binding.title.text = value
        return this
    }

    fun setHeaderTitle(value: Spanned): GoodsCommonHeaderView {
        binding.title.text = value
        return this
    }

    fun setPaddingVertical(dp: Int): GoodsCommonHeaderView {
        binding.title.setPadding(15.dpToPx(), dp.dpToPx(), 0, dp.dpToPx())
        return this
    }

    fun setDivider(isVisible: Boolean): GoodsCommonHeaderView {
        binding.divider2.visibility = if (isVisible) View.VISIBLE else View.GONE
        return this
    }
}