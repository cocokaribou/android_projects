package com.youngin.lunch

open class BaseRepository {
    val service by lazy { BaseApiService.getApiService() }
}