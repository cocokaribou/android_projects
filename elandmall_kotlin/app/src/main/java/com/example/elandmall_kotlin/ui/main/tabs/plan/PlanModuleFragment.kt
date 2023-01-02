package com.example.elandmall_kotlin.ui.main.tabs.plan

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger

class PlanModuleFragment: BaseModuleFragment() {
    override val viewModel: PlanViewModel by viewModels()
    override fun observeData() {

    }
}