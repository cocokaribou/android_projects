package com.example.web_view2.webview

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val uri: Uri = Uri.parse(url)
        val scheme = uri.scheme
        val path = uri.path
        val query = uri.query

        if (URLUtil.isNetworkUrl(url)) {
            view.loadUrl(url)
        }
        if (scheme.equals("tel:")) {

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