package com.example.web_view2

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import com.example.web_view2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //데이터는 따로 가져와야하는 게 맞나보당....
//    private val domain = "https://ch.eqlstore.com/main"
//    private val domain = "http://m.webtoon.daum.net/m/"
//    private val domain = "http://m.comic.naver.com"
    private val domain = "http://m.thence.co.kr/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.e("궁금허네", this.packageName)
        //web settings
        binding.myWebView.settings.apply {
            javaScriptEnabled
            javaScriptCanOpenWindowsAutomatically
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            setSupportMultipleWindows(true)

        }
        if (savedInstanceState == null) {
            binding.myWebView.loadUrl(domain)
        }

        //webview client / webchrome client
        binding.myWebView.webViewClient = MyWebViewClient()
        binding.myWebView.webChromeClient = MyWebChromeClient()

    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(myWebView: WebView, domain: String): Boolean {
            if (URLUtil.isNetworkUrl(domain)) {
                myWebView.loadUrl(domain)
            }
            if (domain.startsWith("tel:")) {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse(domain))
                startActivity(intent)
            }
            return true
        }
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
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