package com.example.youngin.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.youngin.base.BaseActivity
import com.example.youngin.base.BaseApplication

class LandingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent ?: return
        processData(intent.data)
    }

    private fun processData(data: Uri?) {
        data ?: data.toString()
        goLanding()
    }

    private fun goLanding() {
        var i: Intent? = null
        i = Intent(this, MainActivity::class.java)
        if (!BaseApplication.isAppRunning) {
            BaseApplication.showSplash = true
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            BaseApplication.showSplash = false
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            overridePendingTransition(0, 0)
        }
        startActivity(i)
        finish()
    }

}