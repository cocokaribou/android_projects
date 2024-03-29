package com.pionnet.new_webview_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private val webview: BaseWebView by lazy { findViewById(R.id.webview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.webViewClient = MyWebViewClient()
        webview.webChromeClient = MyWebChromeClient(this, onBackPressedDispatcher)
        webview.loadUrl(Config.SITE_URL)
        initBackKey()
    }

    private fun initBackKey() {
        onBackPressedDispatcher.addCallback {
        }
    }
}