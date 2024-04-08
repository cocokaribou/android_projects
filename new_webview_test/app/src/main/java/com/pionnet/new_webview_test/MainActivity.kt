package com.pionnet.new_webview_test

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val webview: BaseWebView by lazy { findViewById(R.id.webview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.webViewClient = WebViewClient()
        webview.webChromeClient = MyWebChromeClient(this, onBackPressedDispatcher)
        webview.loadUrl(Config.SITE_URL)
    }
}