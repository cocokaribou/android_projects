package com.example.elandmall_kotlin.repository

import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.MainGnbResponse

/**
 * cached gnb, home data
 */
object MemDataSource {
    var mainGnbCache : MainGnbResponse? = null
    set(value) {
        field = value.apply {
            this?.data?.gnbList?.filter { it.menuCd != null && it.apiUrl != null }
        }
    }
    var homeCache : HomeResponse? = null
}