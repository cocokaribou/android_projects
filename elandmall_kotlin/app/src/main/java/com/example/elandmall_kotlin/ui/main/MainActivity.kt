package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.base.BaseActivity
import com.example.elandmall_kotlin.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resultNavToSetting = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            // 잘 들어옴
        }
    }
}