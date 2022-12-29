package com.example.elandmall_kotlin.ui.main.tabs.luckydeal

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class LuckyDealModuleFragment : BaseModuleFragment() {
    override var fragmentObserver =
        Observer<SingleLiveEvent<ViewHolderEvent>> {
            it?.getIfNotHandled()?.let { event ->
                Logger.v("lucky! 여기를 타셔야합니다 $event")
            }
        }
    override val viewModel: LuckyDealViewModel by viewModels()
    override fun observeData() {
        viewModel
        viewModel.uiList.observe(this) {
            setModules(it)
        }

    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            LuckyDealModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}