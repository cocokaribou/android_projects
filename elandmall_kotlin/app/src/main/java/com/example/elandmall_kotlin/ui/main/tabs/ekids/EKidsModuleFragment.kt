package com.example.elandmall_kotlin.ui.main.tabs.ekids

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.ViewHolderEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment

class EKidsModuleFragment : BaseModuleFragment() {
    override val viewModel: EKidsViewModel by viewModels()

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }

        EventBus.viewHolderEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let { event ->
                if(event.tabType == TabType.EKIDS) {
                    when (event.eventType) {
                        ViewHolderEventType.CATEGORY_SCROLL1 -> {
                            viewModel.changeWeeklyBestTab(event.content as? Int ?: 0)
                        }
                        ViewHolderEventType.CATEGORY_SCROLL2 -> {
                            viewModel.changeNewArrivalTab(event.content as? Int ?: 0)
                        }
                        ViewHolderEventType.TOGGLE_MORE1 -> {
                            viewModel.toggleWeeklyBest()
                        }
                        ViewHolderEventType.TOGGLE_MORE2 -> {
                            viewModel.toggleNewArrival()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            EKidsModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}