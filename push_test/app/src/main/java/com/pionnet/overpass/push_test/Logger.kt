package com.pionnet.overpass.push_test

import android.util.Log

object Logger {
    const val TAG = "push log"
    private val isDebug = BuildConfig.DEBUG

    fun d(msg: String) {
        if (isDebug) {
            Log.println(Log.DEBUG, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun d(t: String?, msg: String) {
        val tag = t ?: TAG
        if (isDebug) {
            Log.println(Log.DEBUG, tag, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun i(msg: String) {
        if (isDebug) {
            Log.println(Log.INFO, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun i(t: String?, msg: String) {
        val tag = t ?: TAG
        if (isDebug) {
            Log.println(Log.INFO, tag, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun e(msg: String) {
        if (isDebug) {
            Log.println(Log.ERROR, TAG, msg + getStackTrace(object : Throwable() {}))
        }
    }

    fun e(t: String?, msg: String) {
        val tag = t ?: TAG
        if (isDebug) {
            Log.println(Log.ERROR, tag, msg + getStackTrace(object : Throwable() {}))
        }
    }

    private fun getStackTrace(t: Throwable): String {
        if (t.stackTrace.size < 2) return ""
        val e = t.stackTrace[1]
        return " at " + "." + e.methodName + "(" + e.fileName + ":" + e.lineNumber + ")"
    }
}