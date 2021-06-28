package com.example.web_view2.webview

import android.annotation.TargetApi
import android.app.Activity
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
import androidx.core.content.ContextCompat
import com.example.web_view2.activity.MainActivity
import java.util.jar.Manifest

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

            if(!hasPermission()){
                ActivityCompat.requestPermissions((context as Activity), permissions, PHOTO_PERMISSION_RESULT_CODE)
            }else{
                fileChooser()
            }


//            var uploadMessage: ValueCallback<Array<Uri>>? = null //이미지 업로드시 사용
//
//            val acceptType = fileChooserParams!!.acceptTypes[0].toString() //
//
//            if (uploadMessage != null) {
//                uploadMessage!!.onReceiveValue(null)
//            }
//
//            uploadMessage = filePathCallback
//
//            val contentSelectionIntent = Intent()
//
//            contentSelectionIntent.action = Intent.ACTION_GET_CONTENT
//            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
//            contentSelectionIntent.type = "image/*"
//            webView!!.context.startActivity(contentSelectionIntent)
//            (context as MainActivity).startActivityForResult(contentSelectionIntent, REQUEST_FILE_UPLOAD)
        }
        return true

    }
    fun fileChooser() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        (context as Activity).startActivityForResult(Intent.createChooser(i, "File Chooser"), 1002)

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

