package com.example.web_view2.webview

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Message
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.web_view2.activity.MainActivity
import java.util.jar.Manifest
class MyWebChromeClient : WebChromeClient() {
    private var context: Context? = null

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
    }

    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) {
        super.getVisitedHistory(callback)
    }

    //사진 업로드
    //롤리팝 이상 API 5.0+
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        if (fileChooserParams != null) {
            var uploadMessage: ValueCallback<Array<Uri>>? = null //이미지 업로드시 사용

            val acceptType = fileChooserParams!!.acceptTypes[0].toString() //

            if (uploadMessage != null) {
                uploadMessage!!.onReceiveValue(null)
            }

            uploadMessage = filePathCallback

            val contentSelectionIntent = Intent()

            contentSelectionIntent.action = Intent.ACTION_GET_CONTENT
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = "image/*"
            webView!!.context.startActivity(contentSelectionIntent)
//            (context as MainActivity).startActivityForResult(contentSelectionIntent, REQUEST_FILE_UPLOAD)
        }
        return true

    }

}

