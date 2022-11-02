package com.example.elandmall_kotlin.ui.intro

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.base.BaseActivity
import com.example.elandmall_kotlin.common.ApiResult
import com.example.elandmall_kotlin.databinding.ActivityIntroBinding
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.MainActivity
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            if (it != ApiResult.SUCCESS) {
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.alert_error_occurred))
                    .setPositiveButton("ok") { _, _ -> finish() }
                    .create().show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    val sec = if (viewModel.completeTime < 2000) 2000 else viewModel.completeTime
                    delay(sec)

                    launchMain()
                }
            }
        }
    }

    private fun launchMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}