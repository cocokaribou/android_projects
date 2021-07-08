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
import com.example.youngin.base.BaseActivity
import com.example.youngin.common.CommonConst

/**
 * 커스텀 웹크롬 클라이언트
 * - 멀티창 onCreateWindow()
 * - 파일업로드 onShowFileChooser()
 */
class MyWebChromeClient() : WebChromeClient() {
    private val tag = javaClass.simpleName
    val permissions: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null

    private var context: Context? = null
    private var isChildOpen = false
    private lateinit var childWebView: MyWebView
    private lateinit var mWebView: MyWebView


    constructor(context: Context, webView: MyWebView) : this() {
        this.context = context
        mWebView = webView
    }

    /* API 수준별로 파일 경로 다르게 처리 */
    companion object {
        var filePathCallbackNormal: ValueCallback<Uri>? = null
//        var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null
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

                Log.e("$tag checker!", "new window created")

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

    /* For Android 5.0+ */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        //callback 은 한번 오픈할때마다 초기화
        if (filePathCallbackLollipop != null) {
            filePathCallbackLollipop!!.onReceiveValue(null)
        }
        filePathCallbackLollipop = filePathCallback

        //권한 체크
        when ((context as MainActivity).checkPermission()) {
            true -> {
                var intent = fileChooserParams?.createIntent()

                try {
//                    (context as MainActivity).startActivityForResultFileChooser.launch(intent)
                    (context as MainActivity).startForResultUpload.launch(intent)
                } catch (e: ActivityNotFoundException) {
                    filePathCallbackLollipop = null
                    return false
                }
            }
            false -> {
//                (context as MainActivity).showRequestPermission()
                //callback 은 한번 오픈할때마다 초기화
                if (filePathCallbackLollipop != null) {
                    filePathCallbackLollipop?.onReceiveValue(null)
                }
                filePathCallbackLollipop = null
            }
        }
        return true
    }
}

