package com.example.elandmall_kotlin.util

import android.util.Log
import com.example.elandmall_kotlin.BuildConfig

object Logger {
    private var TAG = "Logger"
    private val isDebug = BuildConfig.DEBUG

    /**
     * Tag Init
     */
    fun initTag(tag: String) {
        TAG = tag
    }

    fun v(msg: String) {
        if (isDebug) {
            Log.println(Log.VERBOSE, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun d(msg: String) {
        if (isDebug) {
            Log.println(Log.DEBUG, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun i(msg: String) {
        if (isDebug) {
            Log.println(Log.INFO, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.println(Log.ERROR, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    private fun getStackTrace(t: Throwable): String {
        if (t.stackTrace.size < 2) return ""
        val e = t.stackTrace[1]
        return String.format(" at .%s (%s:%s)", e.methodName, e.fileName, e.lineNumber)
    }
}