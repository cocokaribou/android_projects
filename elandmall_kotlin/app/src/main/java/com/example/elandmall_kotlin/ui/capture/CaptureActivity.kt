package com.example.elandmall_kotlin.ui.capture

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.databinding.ActivityCaptureBinding

class CaptureActivity : BaseActivity() {
    val binding by lazy { ActivityCaptureBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }

        setContentView(binding.root)

        resolveIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {

        initUI()
    }

    private fun initUI() = with(binding){
        close.setOnClickListener { finish() }
    }
}