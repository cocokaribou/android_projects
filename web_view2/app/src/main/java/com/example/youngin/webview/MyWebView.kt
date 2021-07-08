package com.example.youngin.webview

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.youngin.R
import com.example.youngin.databinding.ActivityMainBinding
import com.pionnet.overpass.extension.getAppVersion

/**
 * 커스텀 웹뷰 세팅
 */
class MyWebView : WebView {
    lateinit var mWebViewClient: MyWebViewClient
    lateinit var mChromeClient: MyWebChromeClient

    /*커스텀 웹뷰 사용하기 위해 웹뷰 생성자를 모두 재정의한다*/
    constructor(context: Context) : super(context){
        initView(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initView(context)
    }

    private fun initView(context: Context) {
        /* user agent */
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            context.getString(R.string.user_agent),
            context.let {
                getAppVersion(it)
            } + ": AOS:"
        )

        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            userAgentString = userAgent
        }

        if(com.example.youngin.BuildConfig.DEBUG){
            setWebContentsDebuggingEnabled(true)
        }

        mWebViewClient = MyWebViewClient(context)
        mChromeClient = MyWebChromeClient(context, this)
        webViewClient = mWebViewClient
        webChromeClient = mChromeClient
    }



    /*companion object {
        private fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT in 21..22) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }*/

}