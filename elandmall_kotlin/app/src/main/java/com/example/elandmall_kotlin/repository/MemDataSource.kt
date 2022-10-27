package com.example.elandmall_kotlin.repository

import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.MainGnbResponse

/**
 * cached gnb, home data
 */
object MemDataSource {
    var mainGnbCache : MainGnbResponse? = null
    var homeCache : HomeResponse? = null
}