package com.pionnet.new_webview_test

import android.app.AlertDialog
import android.content.Context
import android.os.Message
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams

var counter = 10

open class MyWebChromeClient(private val mContext: Context, private val onBackCallback: OnBackPressedDispatcher) : WebChromeClient() {
    private val subBaseView: RelativeLayout by lazy { (mContext as AppCompatActivity).findViewById(R.id.sub_webview) }
    private val mainWebView: BaseWebView by lazy { (mContext as AppCompatActivity).findViewById(R.id.webview) }

    init {
        onBackCallback.addCallback {
            if (subBaseView.isVisible) {
                val lastWebView = subBaseView.getChildAt(subBaseView.childCount - 1) as? BaseWebView
                lastWebView?.let {
                    if (it.canGoBack()) {
                        it.goBack()
                    } else {
                        subBaseView.removeView(lastWebView)
                        counter -= 10
                        val secondLastWebView = subBaseView.getChildAt(subBaseView.childCount - 1) as? BaseWebView
                        secondLastWebView?.let {
                            secondLastWebView.evaluateJavascript("revealed();", null);
                        } ?: run {
                            counter = 10
                            subBaseView.removeAllViews()
                            subBaseView.isVisible = false
                            mainWebView.evaluateJavascript("revealed();", null);
                        }
                    }
                }
            }
        }
    }

    override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
        subBaseView.isVisible = true

        val subWebView = BaseWebView(mContext).apply {
            webChromeClient = this@MyWebChromeClient
            webViewClient = WebViewClient() // shouldOverrideUrlLoading = false by default!
            layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            updateLayoutParams<ViewGroup.MarginLayoutParams> {
                setMargins(counter, counter, counter, counter)
            }
        }

        val transport = resultMsg.obj as WebView.WebViewTransport
        transport.webView = subWebView
        resultMsg.sendToTarget()

        subBaseView.addView(subWebView)
        counter += 10

        return true
    }

    override fun onCloseWindow(window: WebView) {
        super.onCloseWindow(window)
    }

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        return jsAlert(message, result)
    }

    override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {
        return jsAlert(message, result)
    }

    private fun jsAlert(message: String, result: JsResult, hasNegativeBtn: Boolean = false): Boolean {
        AlertDialog.Builder(mContext).apply {
            setMessage(message)
            setCancelable(false)
            setPositiveButton(android.R.string.ok) { _, _ -> result.confirm() }
        }.create().show()
        return true
    }
}