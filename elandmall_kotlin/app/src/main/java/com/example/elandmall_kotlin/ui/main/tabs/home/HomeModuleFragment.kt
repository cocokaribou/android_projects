package com.example.elandmall_kotlin.ui.main.tabs.home

import android.os.Bundle
import com.example.elandmall_kotlin.ui.BaseModuleFragment

class HomeModuleFragment : BaseModuleFragment() {

    override fun initUI() = with(binding){
        test.text = arguments?.get(KEY_ITEM_TAB_NAME).toString()
    }

    override fun observeData() {
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