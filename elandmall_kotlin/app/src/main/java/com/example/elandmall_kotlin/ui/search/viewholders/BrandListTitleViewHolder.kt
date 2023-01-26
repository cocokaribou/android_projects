package com.example.elandmall_kotlin.ui.search.viewholders

import android.text.Html
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.model.SearchBrandKeyword
import com.example.elandmall_kotlin.ui.search.SearchBaseViewHolder

class BrandListTitleViewHolder(private val binding: ViewSearchBrandListTitleBinding) : SearchBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? SearchBrandKeyword.NavBrandKeywordItem)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: SearchBrandKeyword.NavBrandKeywordItem) = with(binding) {
        alphabet.text = "\'${data.navBrandKeywordTitle}\'"

        val format = root.context.getString(R.string.search_brand_count_format)
        val string = String.format(format, data.navBrandKeywordCount)

        count.text = Html.fromHtml(string)
    }
}