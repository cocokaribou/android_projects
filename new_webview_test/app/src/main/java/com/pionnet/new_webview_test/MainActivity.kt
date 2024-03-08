package com.pionnet.new_webview_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webview = findViewById<BaseWebView>(R.id.webview)
        val subWebView = findViewById<ConstraintLayout>(R.id.sub_webview)

        webview.webViewClient = object : MyWebViewClient(this) {
            override fun showErrorPage() {
                //do nothing
            }
        }
        webview.webChromeClient = MyWebChromeClient(this, subWebView)
        webview.loadUrl(Config.SITE_URL)
    }
}