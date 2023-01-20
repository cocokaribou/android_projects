package com.example.elandmall_kotlin.ui.search.tabs.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.search.SearchBaseModuleFragment

class SearchPopularFragment : SearchBaseModuleFragment() {
    private val viewModel : SearchPopularViewModel by viewModels()
    override fun observeData() {
        viewModel.uiList.observe(this){
            setModules(it)
        }
    }
}