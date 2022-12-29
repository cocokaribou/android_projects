package com.example.elandmall_kotlin.ui.main.tabs.home

import android.media.metrics.Event
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class HomeModuleFragment : BaseModuleFragment() {
    override var fragmentObserver =
        Observer<SingleLiveEvent<ViewHolderEvent>> {
            it?.getIfNotHandled()?.let { event ->
                Logger.v("home! 여기를 타셔야합니다 $event")
            }
        }

    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        // init (cached data)
        MemDataSource.homeCache?.let {
            viewModel.setHomeModules(it)
        }

        // refresh
        viewModel.refreshedList.observe(this) {
            it?.let {
                viewModel.setHomeModules(it)
            }
        }

        viewModel.uiList.observe(this) {
            setModules(it)
        }

    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            HomeModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}