package com.example.app3

import android.util.Log
//import androidx.viewbinding.BuildConfig

class Logger {

    companion object {
        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.ERROR, "youngin", message + getTraceString(object : Throwable() {})
                )
            }
        }

        fun i(message: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.INFO, "youngin", message + getTraceString(object : Throwable() {})
                )
            }
        }

        fun getTraceString(t: Throwable): String {
            if (t.stackTrace.size < 2) {
                return " "
            }
            val e = t.stackTrace[1]
            return " at .${e.methodName}(${e.fileName}:${e.lineNumber})"
        }
    }
}