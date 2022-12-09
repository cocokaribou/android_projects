package com.example.elandmall_kotlin.ui.main.tabs.best

import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel

class BestModuleFragment:BaseModuleFragment() {
    override val viewModel: BestViewModel by viewModels()
    override fun observeData() {
    }
}