package com.example.elandmall_kotlin.ui

import com.example.elandmall_kotlin.api.ApiManager

open class BaseRepository {
    val service = ApiManager.service()
}