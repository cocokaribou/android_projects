package com.example.elandmall_kotlin.ui.main.tabs.eshop

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment

class EShopModuleFragment : BaseModuleFragment() {
    override val viewModel: EShopViewModel by viewModels()
    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }
    }


    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            EShopModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}
