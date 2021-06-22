package com.example.web_view2

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.web_view2.base.BaseApplication
import com.example.web_view2.databinding.ActivityMainBinding
import com.example.web_view2.webview.MyWebViewClient
import com.pionnet.overpass.extension.getAppVersion
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName //log 찍을 때
    private var hash: String? = null //hash
    private var mCurUrl = "" //url을 비워놓네..
    private lateinit var binding: ActivityMainBinding

    private val url = "https://m.sivillage.com/dispctg/initBeautyMain.siv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance = this

        BaseApplication.isAppRunning = true

        //web settings
        val settings = binding.myWebView.settings
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            instance?.let { it.getString(R.string.user_agent) },
            instance?.let {
                getAppVersion(it)
            } + ": AOS:"

        )
        settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            userAgentString = userAgent
        }

        binding.myWebView.loadUrl(url)

        //webview client / webchrome client
        binding.myWebView.webViewClient = MyWebViewClient()
        binding.myWebView.webChromeClient = MyWebChromeClient()

    }


    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            return false
        }
    }

    //뒤로가기
    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }
    }

    fun startCallIntent(tel: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                100 //permission call
            )
        }else{
            if(tel.isNotEmpty()){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(tel))
                startActivity(intent)
            }
        }
    }

    companion object {
        lateinit var instance: MainActivity
    }
}