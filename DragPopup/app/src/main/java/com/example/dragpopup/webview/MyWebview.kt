package com.example.dragpopup.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class MyWebview : WebView {

    lateinit var mWebViewClient : MyWebViewClient
    lateinit var mWebChromeClient: MyWebChromeClient

    lateinit var mContext : Context

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    fun initView(context: Context) {
        if(isInEditMode){
            return
        }
        with(settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            displayZoomControls = false
            builtInZoomControls = true
            setSupportZoom(true)
            javaScriptCanOpenWindowsAutomatically = true
            textZoom = 100
        }
        mWebChromeClient = MyWebChromeClient()
        mWebViewClient = MyWebViewClient()
        webChromeClient = mWebChromeClient
        webViewClient = mWebViewClient

        mContext = this.context
    }

    override fun loadUrl(url: String) {
        val additionalHeader:MutableMap<String, String> = HashMap()
        additionalHeader["appKind"] = "Android"

        super.loadUrl(url, additionalHeader)
    }

    fun setWebListener(listener: MyWebViewClient.WebViewClientListener){
        mWebViewClient.setWebViewClientListener(listener)
    }

}