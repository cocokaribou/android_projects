package com.example.custom_logview.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.custom_logview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomLog.d("app start!")

        viewModel.requestApis()
        CustomLog.d("debug log!")
        CustomLog.w("warning log!")
        CustomLog.i("info log!")
        setContentView(binding.root)

        initUI()
        initObserve()

    }

    private fun initUI() = with(binding) {
        logview.initBottomTab()

        btn.setOnClickListener {
            logview.toggleLogView()
        }
        swipe.setOnRefreshListener {
            CustomLog.d("refresh api!")
            viewModel.requestApis()
        }
    }

    private fun initObserve() {
        CustomLog.logLiveData.observe(this) {
            binding.logview.submitList(it)
        }
        viewModel.onApiComplete.observe(this) { msg ->
            binding.swipe.isRefreshing = false
            CustomLog.d(msg)
        }
    }
}