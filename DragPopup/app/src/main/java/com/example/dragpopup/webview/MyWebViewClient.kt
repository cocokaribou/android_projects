package com.example.dragpopup.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class MyWebViewClient : WebViewClient() {

    lateinit var mWebViewClientListener: WebViewClientListener

    interface WebViewClientListener {
        fun onPageStarted(url: String)
        fun onPageFinshed(url: String)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        handler!!.proceed()
    }

    fun setWebViewClientListener(listener: WebViewClientListener) {
        mWebViewClientListener = listener
    }
}