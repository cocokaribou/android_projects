//package com.example.web_view2.webview
//
//import android.graphics.Bitmap
//import android.net.Uri
//import android.webkit.URLUtil
//import android.webkit.WebResourceRequest
//import android.webkit.WebView
//import android.webkit.WebViewClient
//
//class MyWebViewClient : WebViewClient(){
//
//    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//        val uri:Uri = Uri.parse(url)
//        val scheme = uri.scheme
//        val path = uri.path
//        val test = uri.authority
//        val tesetes = uri.host
//        val lemme = uri.query
//
////        if(URLUtil.isNetworkUrl(url)){
////        }
////
//    }
//
//    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//        super.onPageStarted(view, url, favicon)
//    }
//
//    override fun onPageFinished(view: WebView?, url: String?) {
//        super.onPageFinished(view, url)
//    }
//
//}