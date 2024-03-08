package com.pionnet.new_webview_test

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

open class MyWebChromeClient(private val mContext: Context, val subWebView: ConstraintLayout) : WebChromeClient() {
    override fun onPermissionRequest(request: PermissionRequest) {

        val requestedResources = request.resources

        for (r in requestedResources) {
            if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE || r == PermissionRequest.RESOURCE_AUDIO_CAPTURE) {
                request.grant(
                    arrayOf(
                        PermissionRequest.RESOURCE_VIDEO_CAPTURE, PermissionRequest.RESOURCE_AUDIO_CAPTURE
                    )
                )

                break
            }
        }
    }

    override fun onShowCustomView(view: View, callback: WebChromeClient.CustomViewCallback) {
        Log.e("youngin", "여기?")

        super.onShowCustomView(view, callback)
    }

    override fun onShowCustomView(view: View, requestedOrientation: Int, callback: WebChromeClient.CustomViewCallback) {
        this.onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
    }

    private fun setFullscreen(enabled: Boolean) {
        val win = (mContext as Activity).window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_FULLSCREEN

        if (enabled) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }

        win.attributes = winParams
    }

    private class FullscreenHolder(ctx: Context) : FrameLayout(ctx) {
        init {
            setBackgroundColor(ContextCompat.getColor(ctx, android.R.color.black))
        }

        override fun onTouchEvent(evt: MotionEvent): Boolean {
            return true
        }
    }

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        return jsAlert(message, result, false)
    }

    override fun onJsConfirm(view: WebView, url: String, message: String, result: JsResult): Boolean {


        return jsAlert(message, result, true)
    }

    override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
        subWebView.isVisible = true
        val webview = subWebView.findViewById<BaseWebView>(R.id.base_webview)

        webview.loadUrl(view.url ?: "")
        return true
    }

    override fun onCloseWindow(window: WebView) {
        super.onCloseWindow(window)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
    }

    /**
     * Js alert.
     *
     * @param message        the message
     * @param result         the result
     * @param hasNegativeBtn the has negative btn
     * @return true, if successful
     */
    private fun jsAlert(message: String, result: JsResult, hasNegativeBtn: Boolean): Boolean {
        if ((mContext as Activity).isFinishing) {
            return false
        }
        Log.e("youngin", "js alert")
        return true
    }
}