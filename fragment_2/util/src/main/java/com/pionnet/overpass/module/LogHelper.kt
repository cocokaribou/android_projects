package com.pionnet.overpass.module


import android.util.Log
import com.pionnet.overpass.BuildConfig

/**
 * LogHelper
 * - 디버그에서만 출력됨
 * - 단일 인자로도 사용가능
 * - application 단에서 initTag(tag : String)로 디폴트 태그 초기화
 */
object LogHelper {
    private var TAG = "LogHelper"
    private val isDebug = BuildConfig.DEBUG

    fun initTag(tag: String) {
        TAG = tag
    }

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