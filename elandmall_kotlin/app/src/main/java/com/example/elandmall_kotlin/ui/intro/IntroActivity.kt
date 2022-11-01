package com.example.elandmall_kotlin.ui.intro

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.base.BaseActivity
import com.example.elandmall_kotlin.common.ApiResult
import com.example.elandmall_kotlin.databinding.ActivityIntroBinding
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.MainActivity
import com.example.elandmall_kotlin.util.Logger

/**
 * IntroActivity
 * - check intro data
 * - cache gnb, home data
 * - launch main activity after 2sec delay
 */
class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel>(R.layout.activity_intro) {
    override val viewModel by viewModels<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
        initObserve()
    }

    private fun initUI() {
        // NOP
    }

    private fun initObserve() {
        viewModel.mainFlag.observe(this) {
            AlertDialog.Builder(this).apply {
                if (it == ApiResult.SUCCESS) {
                    setMessage("메인으로 진입합니다.")
                    setPositiveButton("ok") { _, _ -> launchMain() }
                } else {
                    setMessage("오류가 발생하였습니다.")
                    setPositiveButton("ok") { _, _ -> finish() }
                }
            }.create().show()

            Logger.i(MemDataSource.mainGnbCache.toString())
            Logger.i(MemDataSource.homeCache.toString())
        }
    }

    private fun launchMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}