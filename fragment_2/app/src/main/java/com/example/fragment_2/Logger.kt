package com.example.fragment_2

import android.util.Log

class Logger {
    companion object {
        fun e(s: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.ERROR, "youngin", s + getTraceString(object : Throwable() {})
                )
            }
        }

        fun d(s: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.DEBUG, "youngin", s + getTraceString(object : Throwable() {})
                )
            }
        }

        fun i(s: String) {
            if (BuildConfig.DEBUG) {
                Log.println(
                    Log.INFO, "youngin", s + getTraceString(object : Throwable() {})
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