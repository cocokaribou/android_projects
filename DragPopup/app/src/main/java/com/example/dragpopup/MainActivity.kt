package com.example.dragpopup

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dragpopup.databinding.ActivityMainBinding
import com.example.dragpopup.webview.MyWebViewClient

class MainActivity : AppCompatActivity(), MyWebViewClient.WebViewClientListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMainUi()
    }

    fun setMainUi() {
        binding.webview.loadUrl("https://m-spao.elandmall.com/main/initMain.action")
        binding.webview.setWebListener(this)
    }


    override fun onPageStarted(url: String) {
        Log.e(javaClass.simpleName,"onPageStarted")
    }

    override fun onPageFinshed(url: String) {
        TODO("Not yet implemented")
    }


}