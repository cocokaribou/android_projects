package com.example.elandmall_kotlin.ui.main.tabs.luckydeal

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.ekids.EKidsModuleFragment
import com.example.elandmall_kotlin.util.Logger

class LuckyDealFragment : BaseModuleFragment() {
    override val viewModel: LuckyDealViewModel by viewModels()
    override fun observeData() {
        viewModel
        viewModel.uiList.observe(this) {
            setModules(it)
        }
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            LuckyDealFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}