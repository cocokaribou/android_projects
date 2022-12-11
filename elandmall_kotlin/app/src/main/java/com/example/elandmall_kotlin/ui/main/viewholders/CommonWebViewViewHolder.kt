package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebSettings.LOAD_NO_CACHE
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewCommonWebViewBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.setHtmlDoc

class CommonWebViewViewHolder(private val binding: ViewCommonWebViewBinding) : BaseViewHolder(binding.root) {
    private val mWebViewClient by lazy { CommonWebViewClient() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonWebViewData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.CommonWebViewData) = with(binding) {
        val mWebView = WebView(root.context).apply {
            webViewClient = mWebViewClient
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                builtInZoomControls = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                cacheMode = LOAD_NO_CACHE
                mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            }
            setInitialScale(1)
            loadDataWithBaseURL(null, data.contentHtml.setHtmlDoc(), "text/html", "UTF-8", null);
        }
        if (webview.childCount == 0) {
            // for wrap_content option
            webview.addView(mWebView)
        }

        if (!data.shareUrl.isNullOrEmpty()) {
            share.visibility = View.VISIBLE
            share.setOnClickListener {
                EventBus.fire(LinkEvent(data.shareUrl))
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