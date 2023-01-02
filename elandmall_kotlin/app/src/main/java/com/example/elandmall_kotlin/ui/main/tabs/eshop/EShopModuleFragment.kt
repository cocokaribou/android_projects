package com.example.elandmall_kotlin.ui.main.tabs.eshop

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger

class EShopModuleFragment:BaseModuleFragment() {
    override val viewModel: EShopViewModel by viewModels()
    override fun observeData() {
    }
}