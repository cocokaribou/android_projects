package com.pionnet.overpass.extension

import android.app.Activity
import android.util.DisplayMetrics

/**
 * 핸드폰 해상도 구하기 
 */
fun getDisplaySize(activity: Activity): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val display = activity.display
        display?.getRealMetrics(displayMetrics)
    } else {
        @Suppress("DEPRECATION")

        val display = activity.windowManager.defaultDisplay
        @Suppress("DEPRECATION")
        display.getMetrics(displayMetrics)
    }

    return displayMetrics
}