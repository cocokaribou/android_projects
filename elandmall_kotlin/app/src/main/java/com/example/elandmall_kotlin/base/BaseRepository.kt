package com.example.elandmall_kotlin.base

import com.example.elandmall_kotlin.api.ApiManager

open class BaseRepository {
    val service = ApiManager.service()
}