package com.example.elandmall_kotlin.common

/**
 * global-scope constants
 */
object CommonConst {
    val mainDomain = "http://m.elandmall.com"
}

// api result flag
enum class ApiResult {
    SUCCESS,
    FAIL,
    EXCEPTION
}