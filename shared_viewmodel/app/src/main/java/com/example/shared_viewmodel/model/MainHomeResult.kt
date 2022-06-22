package com.example.shared_viewmodel.model

sealed class MainHomeResult {
    data class Success(val data: HomeResponse) : MainHomeResult()
    data class Error(val error: Exception) : MainHomeResult()
}