package com.example.elandmall_kotlin.ui.main.tabs.plan

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger

class PlanModuleFragment: BaseModuleFragment() {
    override var fragmentObserver =
        Observer<SingleLiveEvent<ViewHolderEvent>> {
            it?.getIfNotHandled()?.let { event ->
                Logger.v("plan! 여기를 타셔야합니다 $event")
            }
        }
    override val viewModel: PlanViewModel by viewModels()
    override fun observeData() {

    }
}