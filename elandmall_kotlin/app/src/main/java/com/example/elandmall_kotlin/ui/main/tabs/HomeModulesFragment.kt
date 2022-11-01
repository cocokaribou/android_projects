package com.example.elandmall_kotlin.ui.main.tabs

import android.os.Bundle
import android.view.View
import com.example.elandmall_kotlin.base.BaseFragment

class HomeModulesFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserve()
    }

    private fun initUI() {}

    private fun initObserve() {}

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            HomeModulesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}