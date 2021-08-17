package com.example.push_test

import android.util.Log
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class MyWebChromeClient:WebChromeClient() {
    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean {
        Log.e("WebChromeClient", "view=$view, url=$url, message=$message, defaultValue=$defaultValue, result=$result")
        return super.onJsPrompt(view, url, message, defaultValue, result)
    }

    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        Log.e("WebChromeClient", "view=$view, url=$url, message=$message, result=$result")
        return super.onJsConfirm(view, url, message, result)
    }

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        Log.e("WebChromeClient", "view=$view, url=$url, message=$message, result=$result")
        return super.onJsAlert(view, url, message, result)
    }

    override fun onRequestFocus(view: WebView?) {
        Log.e("WebChromeClient", "onRequestFocus()")
        super.onRequestFocus(view)
    }

    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
        Log.e("WebChromeClient", "message=$message, lineNumber=$lineNumber, sourceId=$sourceID")
    }
}