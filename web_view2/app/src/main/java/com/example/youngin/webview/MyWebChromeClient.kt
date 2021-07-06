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
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import com.example.youngin.activity.MainActivity

class MyWebChromeClient(private val context: Context) : WebChromeClient() {
    private val PHOTO_PERMISSION_RESULT_CODE = 1138
    val permissions: Array<String> = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    var filePathCallbackNormal: ValueCallback<Uri>? = null
    var filePathCallbackLollipop: ValueCallback<Array<Uri>>? = null

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
                context,
                permissions[0]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }
}

