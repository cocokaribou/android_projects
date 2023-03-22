package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCommonWebViewBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsDetailWebBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewMoreItemBinding
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.setHtmlDoc

class GoodsDetailWebHolder(val binding: ViewGoodsDetailWebBinding) : GoodsBaseViewHolder(binding.root) {
    private val mWebViewClient by lazy { CommonWebViewClient() }

    private var isExpand = false
    private var toggleListener: ((Boolean) -> Unit)? = null
    override fun onBind(item: Any?) {
        (item as? Map<*, *>)?.let {
            isExpand = it["isExand"] as? Boolean ?: false
            toggleListener = it["toggle"] as? (Boolean) -> Unit

            val data = it["data"] as? String ?: ""
            initUI(data)
        }
    }

    private fun initUI(data: String) = with(binding) {
        val mWebView = WebView(root.context).apply {
            webViewClient = mWebViewClient
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                builtInZoomControls = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            }
            setInitialScale(1)
            loadUrl(data)
        }
        if (webview.childCount == 0) {
            webview.addView(mWebView)
        }

        toggle.setOnClickListener {
            isExpand = !isExpand
            toggleListener?.invoke(isExpand)

            if (isExpand) {
                webview.layoutParams.height = WRAP_CONTENT
                webview.requestLayout()

                toggle.visibility = View.GONE
            }
        }
    }

    inner class CommonWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            EventBus.fire(LinkEvent(request?.url.toString()))
            return true
        }
    }

}