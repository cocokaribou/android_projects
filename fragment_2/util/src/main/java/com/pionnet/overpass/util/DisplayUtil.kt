package com.pionnet.overpass.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

fun getDisplaySize(context: Context):DisplayMetrics{
    var displayMetrics = DisplayMetrics()
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        displayMetrics = context.resources.displayMetrics
    } else {
        @Suppress("DEPRECATION")
        val display = (context as Activity).windowManager.defaultDisplay

        @Suppress("DEPRECATION")
        display.getMetrics(displayMetrics)
    }
    return displayMetrics
}
