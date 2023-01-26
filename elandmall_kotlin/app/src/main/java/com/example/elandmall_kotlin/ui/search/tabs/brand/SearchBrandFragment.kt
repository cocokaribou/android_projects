package com.example.elandmall_kotlin.ui.search.tabs.brand

import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.model.SearchModuleType
import com.example.elandmall_kotlin.ui.search.SearchBaseModuleFragment
import com.example.elandmall_kotlin.util.Logger

class SearchBrandFragment: SearchBaseModuleFragment() {
    private val viewModel : SearchBrandViewModel by viewModels()
    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }
    }
}