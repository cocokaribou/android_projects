package com.example.sampleapp

import android.util.Log
import com.example.sampleapp.BuildConfig

class Logger {

    companion object {
        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.ERROR, "yougnin", message
                )
            }
        }

        fun i(message: String){
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.INFO, "youngin", message
                )
            }
        }
    }
}