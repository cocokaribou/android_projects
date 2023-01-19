package com.example.elandmall_kotlin.ui.search

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ActivityIntroBinding
import com.example.elandmall_kotlin.databinding.ActivitySearchBinding
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.util.Logger

class SearchActivity : BaseActivity() {
    val viewModel: SearchViewModel by viewModels()
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }

        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() {

    }

    private fun initObserve() {

    }
}