package com.siv.du.webview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import com.siv.du.R
import com.pionnet.overpass.extension.getAppVersion
import com.pionnet.overpass.extension.getCookies
import com.pionnet.overpass.extension.getCookieForName
import com.pionnet.overpass.extension.logout

/**
 * 커스텀 웹뷰 세팅
 */
class MyWebView : WebView {
    lateinit var mWebViewClient: MyWebViewClient
    lateinit var mChromeClient: MyWebChromeClient

    /*커스텀 웹뷰 사용하기 위해 웹뷰 생성자를 모두 재정의한다*/
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    interface WebViewScrollListener {
        fun onScrollTop(top: Int)
        fun onScrollDown(top: Int)
        fun onBottomReached()
    }

    private fun initView(context: Context) {
        /* user agent */
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            context.getString(R.string.user_agent),
            getAppVersion(context) + ": AOS:"
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
            textZoom = 100
        }

        if (com.siv.du.BuildConfig.DEBUG) {
            setWebContentsDebuggingEnabled(true)
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            cookieManager.setAcceptThirdPartyCookies(this, true)
        }

        mWebViewClient = MyWebViewClient(context)
        mChromeClient = MyWebChromeClient(context, this)
        webViewClient = mWebViewClient
        webChromeClient = mChromeClient

    }

    override fun loadUrl(url: String) {
        val extraHeaders: MutableMap<String, String> = HashMap()
        var autoLogin = getCookieForName("https://.sivillage.com", "AUTO_LOGIN_YN")
        Log.e("$tag checker!!!", "$autoLogin")

        if (autoLogin != "Y") {
            logout()
        }
        loadUrl(url, extraHeaders)
    }

}