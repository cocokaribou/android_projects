package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.home.HomeViewModel
import com.example.elandmall_kotlin.util.Logger

class StoreShopModuleFragment:BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()
    override fun observeData() {
    }

}