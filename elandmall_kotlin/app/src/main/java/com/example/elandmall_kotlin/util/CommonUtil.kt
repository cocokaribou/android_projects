package com.example.elandmall_kotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        (nc != null &&
                (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)))
    } else {
        val activeNetworkInfo = cm.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}