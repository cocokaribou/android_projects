package com.example.elandmall_kotlin.ui.main.tabs.eshop

import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel

class EShopModuleFragment:BaseModuleFragment() {
    override val viewModel: EShopViewModel by viewModels()
    override fun observeData() {
    }
}