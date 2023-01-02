package com.example.elandmall_kotlin.ui.main.tabs.web

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.SingleLiveEvent
import com.example.elandmall_kotlin.ui.ViewHolderEvent
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class WebviewModulesFragment : BaseModuleFragment() {
    override val viewModel: WebviewViewModel by viewModels()

    override fun observeData() {
        binding.test.text = arguments?.get(KEY_ITEM_TAB_NAME).toString()
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            WebviewModulesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}