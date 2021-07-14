package com.siv.du.webview

import android.Manifest
import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Message
import android.util.Log
import android.webkit.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.siv.du.R
import com.siv.du.activity.MainActivity
import com.siv.du.common.CommonConst

/**
 * 커스텀 웹크롬 클라이언트
 * - 멀티창 onCreateWindow()
 * - 파일업로드 onShowFileChooser()
 */
class MyWebChromeClient() : WebChromeClient() {
    private val tag = javaClass.simpleName

    var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null
    var mFileChooserParam: FileChooserParams? = null

    private var context: Context? = null

    private var isChildOpen = false
    private lateinit var childWebView: MyWebView
    private lateinit var mWebView: MyWebView


    constructor(context: Context, webView: MyWebView) : this() {
        this.context = context
        mWebView = webView
    }

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        Log.e("checker!", "will it be invoked?")
        return super.onJsAlert(view, url, message, result)


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

    fun closeChild() {
        isChildOpen = false
        mWebView.removeView(childWebView)
        childWebView.destroy()
    }

    /* For Android 5.0+ */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        val main = context as MainActivity

        if (filePathCallbackLollipop != null) {
            filePathCallbackLollipop!!.onReceiveValue(null)
        }
        filePathCallbackLollipop = filePathCallback
        mFileChooserParam = fileChooserParams

        //권한 체크
        when (main.checkPermission(Manifest.permission.CAMERA)) {
            true -> {
                fileUpload()
            }
            false -> {
                //권한 요청
                main.askPermissions(Manifest.permission.CAMERA)

                if (filePathCallbackLollipop != null) {
                    filePathCallbackLollipop?.onReceiveValue(null)
                }
                filePathCallbackLollipop = null
            }
        }
        return true
    }

    fun fileUpload():Boolean{
        val intent = mFileChooserParam?.createIntent()
        return try {
            (context as MainActivity).startForResultUpload.launch(intent)
            true
        } catch (e: ActivityNotFoundException) {
            filePathCallbackLollipop = null
            false
        }
    }

}

