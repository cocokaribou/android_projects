package com.example.elandmall_kotlin.ui.search.tabs.recently

import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.ui.search.SearchRepository

class SearchRecentlyViewModel: ViewModel() {
    private val repository by lazy { SearchRepository() }

}