package com.example.elandmall_kotlin.ui.main.tabs.ekids

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class EKidsModuleFragment : BaseModuleFragment() {
    override val viewModel: EKidsViewModel by viewModels()

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
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