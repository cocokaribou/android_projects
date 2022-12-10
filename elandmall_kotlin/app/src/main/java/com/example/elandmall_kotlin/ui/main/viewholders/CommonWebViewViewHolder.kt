package com.example.elandmall_kotlin.ui.main.viewholders

import com.example.elandmall_kotlin.databinding.ViewCommonWebViewBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class CommonWebViewViewHolder(private val binding: ViewCommonWebViewBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonWebViewData)?.let {
            initUI(it)
        }
    }

    fun initUI(data: ModuleData.CommonWebViewData) = with(binding){
        webivew.settings.apply {
            javaScriptEnabled = true
        }
        webivew.loadData(data.contentHtml, "text/html; charset=utf-8", "UTF-8");
    }
}