package com.example.udp_client_app

import android.util.Log

class Logger(private val msg: String) {

    init {
        val TAG = "Logger"
        Log.v(TAG, msg)
    }
}