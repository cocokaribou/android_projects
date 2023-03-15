package com.example.elandmall_kotlin.ui.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elandmall_kotlin.databinding.ActivityWebviewBinding

class WebViewActivity: AppCompatActivity() {
    private val binding get() = _binding!!
    private var _binding : ActivityWebviewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}