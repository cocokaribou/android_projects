package com.example.elandmall_kotlin.ui.goods.viewholders.tab1

import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.databinding.ViewCommonWebViewBinding
import com.example.elandmall_kotlin.model.GoodsResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.setHtmlDoc

class GoodsDetailWebHolder(val binding: ViewCommonWebViewBinding) : GoodsBaseViewHolder(binding.root) {
    private val mWebViewClient by lazy { CommonWebViewClient() }
    override fun onBind(item: Any?) {
        (item as? String)?.let {
            initUI(it)
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
            // for wrap_content option
            webview.addView(mWebView)
        }
    }

    inner class CommonWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            EventBus.fire(LinkEvent(request?.url.toString()))
            return true
        }
    }

}