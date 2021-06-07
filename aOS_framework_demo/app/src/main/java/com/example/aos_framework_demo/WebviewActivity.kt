package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aos_framework_demo.databinding.ActivityWebviewBinding

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url").toString()

        with(binding) {
            webviewStore.settings.javaScriptEnabled = true
            if (!url.isNullOrEmpty()) {
                webviewStore.loadUrl(url)
            }
        }
    }
}