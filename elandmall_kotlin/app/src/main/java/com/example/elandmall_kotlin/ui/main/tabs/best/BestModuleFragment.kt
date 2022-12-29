package com.example.elandmall_kotlin.ui.main.tabs.best

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.luckydeal.LuckyDealModuleFragment
import com.example.elandmall_kotlin.util.Logger

class BestModuleFragment:BaseModuleFragment() {
    override var fragmentObserver =
        Observer<SingleLiveEvent<ViewHolderEvent>> {
            it?.getIfNotHandled()?.let { event ->
                Logger.v("best! 여기를 타셔야합니다 $event")
            }
        }
    override val viewModel: BestViewModel by viewModels()
    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            BestModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}