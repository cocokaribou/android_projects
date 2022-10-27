package com.example.elandmall_kotlin.common

/**
 * test flag
 */
object AppConfig {
    val USE_LOCAL_DATA = false
    val USE_LOCAL_SIGNED_KEY = USE_LOCAL_DATA || false
    val USE_LOCAL_PRELOAD = USE_LOCAL_DATA || false
    val USE_LOCAL_GNB = USE_LOCAL_DATA || false
    val USE_LOCAL_FOOTER = USE_LOCAL_DATA || false
    val USE_LOCAL_SPLASH = USE_LOCAL_DATA || false
    val USE_LOCAL_POP_NOTI = USE_LOCAL_DATA || false
    val USE_LOCAL_CART_CNT = USE_LOCAL_DATA || false
    val USE_LOCAL_HOME = USE_LOCAL_DATA || false
    val USE_LOCAL_SLIDE = USE_LOCAL_DATA || false
}