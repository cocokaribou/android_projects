package com.example.elandmall_kotlin.ui.main.tabs

import android.os.Bundle
import android.view.View
import com.example.elandmall_kotlin.base.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class WebModulesFragment: BaseModuleFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.v("web created!")

        initUI()
        observeData()
    }

    private fun initUI() {
        binding.text.text = "web"
    }

    private fun observeData() {}

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