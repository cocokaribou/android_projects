package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()
    override fun observeData() {
        viewModel.storeShopList.observe(this) {
            viewModel.setStoreShopModules(it)
        }

        viewModel.uiList.observe(this) {
            setModules(it)
        }
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            StoreShopModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}