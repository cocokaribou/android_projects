package com.example.youngin.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.youngin.R
import com.example.youngin.activity.MainActivity
import com.example.youngin.databinding.ActivityMainBinding
import com.pionnet.overpass.extension.getAppVersion

class MyWebView : WebView {

    private lateinit var binding:ActivityMainBinding
    var chromeClient: MyWebChromeClient

    constructor(context:Context) : super(context){

    }

    init{
        chromeClient = MyWebChromeClient(context)
    }
}