package com.siv.du.network

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkManager private constructor(private val context: Context) {
    interface OnNetworkListener {
        fun networkAvailable()
        fun finishApp()
    }
    companion object {
        fun getInstance(context: Context): NetworkManager {
            return NetworkManager(context)
        }
    }

    fun checkNetworkAvailable(listener: OnNetworkListener) {
        if (isNetworkAvailable(context)) {
            listener.networkAvailable()
        }else{
            AlertDialog.Builder(context)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw    = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

}