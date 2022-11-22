package com.example.elandmall_kotlin.ui.main.tabs.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class HomeModuleFragment : BaseModuleFragment() {

    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        // init (cached data)
        MemDataSource.homeCache?.let {
            viewModel.setHomeModules(it)
        }

        // refresh
        viewModel.refreshedData.observe(this) {
            it?.let {
                viewModel.setHomeModules(it)
            }
        }

        viewModel.homeList.observe(this) {
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