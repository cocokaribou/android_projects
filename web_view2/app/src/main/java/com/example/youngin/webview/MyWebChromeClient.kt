package com.example.youngin.webview

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.example.youngin.R
import com.example.youngin.activity.MainActivity
import com.example.youngin.common.CommonConst

class MyWebChromeClient() : WebChromeClient() {
    private val PHOTO_PERMISSION_RESULT_CODE = 1138
    val permissions: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    private var context: Context? = null
    private var isChildOpen = false
    private lateinit var childWebView: MyWebView
    private lateinit var mWebView: MyWebView

    var filePathCallbackNormal: ValueCallback<Uri>? = null
    var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null

    constructor(context: Context): this() {
        this.context = context
    }

    constructor(context: Context, webView: MyWebView) : this() {
        this.context = context
        mWebView = webView
    }

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        isChildOpen = true
        childWebView = MyWebView(context!!)
        childWebView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        mWebView!!.scrollTo(0, 0)

        childWebView.settings.apply {
            javaScriptEnabled = true
            textZoom = 100 // 안드로이드 글꼴 크기 변경 방지
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
        }

        childWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                val uri = Uri.parse(url)
                val scheme = uri.scheme
                val host = uri.host

                Log.d("create", url)

                if (url.startsWith(context!!.getString(R.string.design_overpass))) {
                    mWebView.loadUrl(url)
                    return true
                } else if (scheme == CommonConst.SCHEME_BRIDGE) {
                    if (host.equals("external browser")) {
                        val link = uri.getQueryParameter("link")
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        (context as MainActivity).startActivity(intent)
                    }
                    return true
                } else {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    (context as MainActivity).startActivity(intent)
                    return true
                }
            }
        }
        return false
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
    }

    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) {
        super.getVisitedHistory(callback)
    }

    fun isChildOpen(): Boolean {
        return isChildOpen
    }

    //For Android 5.0+
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        filePathCallbackLollipop?.onReceiveValue(null)
        filePathCallbackLollipop = filePathCallback

        if (fileChooserParams != null) {

            if (!hasPermission()) {
                ActivityCompat.requestPermissions(
                    (context as MainActivity),
                    permissions,
                    PHOTO_PERMISSION_RESULT_CODE
                )
            } else {
                fileChooser()
            }
        }
        return true

    }

    private fun fileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        try {
            (context as MainActivity).startActivityForResultFileChooser(
                Intent.createChooser(
                    intent,
                    "File Chooser"
                )
            )
        } catch (e: ActivityNotFoundException) {

        }

    }


    private fun hasPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                permissions[0]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}

