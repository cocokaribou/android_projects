package com.example.elandmall_kotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elandmall_kotlin.base.BaseApplication
import com.example.elandmall_kotlin.common.CommonConst.HOST_LANDING
import com.example.elandmall_kotlin.common.CommonConst.SCHEME_ELANDMALL
import com.example.elandmall_kotlin.ui.intro.IntroActivity
import com.example.elandmall_kotlin.ui.main.MainActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val landingIntent = if (BaseApplication.instance.isAppRunning) {
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        } else {
            Intent(this, IntroActivity::class.java).apply {
                addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                )
            }
        }
        prepareLanding(landingIntent)
    }

    // kakaolink
    // elandmall://landing
    private fun prepareLanding(landingIntent: Intent) {
        intent.dataString?.let {
            when {
                it.isDeepLink() -> {}
                it.isKakaoLink() -> {}
            }
        }

        startActivity(landingIntent)
    }

    private fun String.isKakaoLink(): Boolean {
        val uri = Uri.parse(this)
        return getString(R.string.kakao_scheme) == uri.scheme && getString(R.string.kakaolink_host) == uri.host
    }

    private fun String.isDeepLink(): Boolean {
        val uri = Uri.parse(this)
        return SCHEME_ELANDMALL == uri.scheme && HOST_LANDING == uri.host
    }
}