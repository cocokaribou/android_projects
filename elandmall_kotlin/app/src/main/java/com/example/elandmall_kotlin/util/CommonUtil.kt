package com.example.elandmall_kotlin.util

import android.content.Context
import android.content.res.AssetManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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

fun getJsonFileToString(filePath: String, context: Context): String {
    var result = ""
    val assetManager: AssetManager = context.assets
    try {
        val `is` = assetManager.open(filePath!!)
        val isr = InputStreamReader(`is`)
        val reader = BufferedReader(isr)
        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(
                """$line""".trimIndent()
            )
            line = reader.readLine()
        }
        result = buffer.toString()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return result
}