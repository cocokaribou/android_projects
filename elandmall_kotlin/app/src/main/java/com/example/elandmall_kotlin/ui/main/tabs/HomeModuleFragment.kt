package com.example.elandmall_kotlin.ui.main.tabs

import android.os.Bundle
import android.view.View
import com.example.elandmall_kotlin.base.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class HomeModuleFragment : BaseModuleFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.v("home created!")
        initUI()
        observeData()

        val test = mutableListOf<String>().toList()

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