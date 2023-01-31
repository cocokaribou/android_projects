package com.example.elandmall_kotlin.ui.intro

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.common.ApiResult
import com.example.elandmall_kotlin.databinding.ActivityIntroBinding
import com.example.elandmall_kotlin.MainActivity
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
class IntroActivity : BaseActivity() {
    val viewModel by viewModels<IntroViewModel>()
    val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        initUI()
        initObserve()
    }

    private fun initUI() {
        // NOP
    }

    private fun initObserve() {
        viewModel.mainFlag.observe(this, object: Observer<ApiResult>{
            override fun onChanged(t: ApiResult?) {
                if (t != ApiResult.SUCCESS) {
                    AlertDialog.Builder(this@IntroActivity)
                        .setMessage(getString(R.string.alert_error_occurred))
                        .setPositiveButton("ok") { _, _ -> finish() }
                        .create().show()
                } else {
                    viewModel.mainFlag.removeObserver(this)
                    CoroutineScope(Dispatchers.IO).launch {
                        val sec = if (viewModel.completeTime < 2000) 2000 else viewModel.completeTime
                        delay(sec)

                        launchMain()
                    }
                }
            }
        })
    }

    private fun launchMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}