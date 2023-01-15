package com.example.elandmall_kotlin.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.CategoryResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val repository by lazy { CategoryRepository() }
    init {
        requestCategory()
    }

    private fun requestCategory() {
        viewModelScope.launch {
            repository.requestCategoryStream()
                .collect{
                    it.fold(
                        onSuccess = {},
                        onFailure = {}
                    )
                }
        }
    }

}