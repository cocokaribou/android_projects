package com.example.web_view2

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import com.example.web_view2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val url = "https://m.sivillage.com/dispctg/initBeautyMain.siv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //web settings
        binding.myWebView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false

        }
        binding.myWebView.loadUrl(url)

        //webview client / webchrome client
        binding.myWebView.webViewClient = MyWebViewClient()
        binding.myWebView.webChromeClient = MyWebChromeClient()

    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(myWebView: WebView, url: String): Boolean {
            Log.e("어디로 가는지", url)
            if (URLUtil.isNetworkUrl(url)) {
                myWebView.loadUrl(url)
            }
            if (url.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse(url))
                startActivity(intent)
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

    }

    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            return false
        }
    }

    //뒤로가기
    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }

    }
}