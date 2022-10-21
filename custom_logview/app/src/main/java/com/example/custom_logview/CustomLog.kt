package com.example.custom_logview

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import okhttp3.Request
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

enum class LogType {
    REQUEST,
    RESPONSE,
    D,
    I,
    W
}

class LogData(
    val type: LogType,
    val url: String,
    val length: String,
    val method: String,
    val code: Int,
    val input: Any,
    val time: String,
) {
    val isApiLog = type == LogType.RESPONSE || type == LogType.REQUEST

    constructor(request: Request) : this(
        type = LogType.REQUEST,
        url = request.url.encodedPath,
        length = request.body?.contentLength().toString(),
        method = request.method,
        code = 0,
        time = "",
        input = request,
    )

    constructor(response: Response) : this(
        type = LogType.RESPONSE,
        url = response.request.url.encodedPath,
        length = if (response.headers["Content-Length"] != null) response.headers["Content-Length"] + "-byte" else "unknown-length",
        method = "",
        code = response.code,
        input = response,
        time = (response.receivedResponseAtMillis - response.sentRequestAtMillis).toString(),
    )

    constructor(string: String, type: LogType) : this(
        type = type,
        url = "",
        length = "",
        method = "",
        code = 0,
        input = string,
        time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", Locale.KOREA).format(Date()).toString(),
    )

    val msg: String
        get() {
            return when(type) {
                LogType.REQUEST -> "--> $method $url (${length}-byte body)"
                LogType.RESPONSE -> "<-- $code $url (${time}ms, $length body)"
                else -> "$time $input"
            }
        }

    val body: String
        get() {
            // TODO
//            when(type) {
//                LogType.REQUEST -> {
//                    input as Request
//
//                    input.
//                }
//                LogType.RESPONSE -> {
//
//                }
//                else -> {
//
//                }
//            }
            return "body"
        }

    val color: Int
        get() {
            return when(type) {
                LogType.REQUEST -> Color.parseColor("#7DC8CA")
                LogType.RESPONSE -> Color.parseColor("#F3D2AC")
                LogType.D -> Color.parseColor("#4b86e7")
                LogType.I -> Color.parseColor("#02ff02")
                else -> Color.parseColor("#ffff07")
            }
        }
}


object CustomLog {
    val logList = mutableListOf<LogData>()
    val logLiveData = MutableLiveData<List<LogData>>()

    fun a(response: Response) {
        logList.add(LogData(response))
        logLiveData.postValue(logList.reversed())
    }

    fun a(request: Request) {
        logList.add(LogData(request))
        logLiveData.postValue(logList.reversed())
    }

    fun d(string: String) {
        val msg = string + getStackTrace(object : Throwable() {})
        logList.add(LogData(msg, LogType.D))
        logLiveData.postValue(logList.reversed())
    }

    fun i(string: String) {
        val msg = string + getStackTrace(object : Throwable() {})
        logList.add(LogData(msg, LogType.I))
        logLiveData.postValue(logList.reversed())
    }

    fun w(string:String) {
        val msg = string + getStackTrace(object : Throwable() {})
        logList.add(LogData(msg, LogType.W))
        logLiveData.postValue(logList.reversed())
    }

    fun clear() {
        logList.clear()
        logLiveData.postValue(mutableListOf())
    }

    fun getStackTrace(t: Throwable): String {
        if (t.stackTrace.size < 2) return ""
        val e = t.stackTrace[1]
        return "\nat " + "." + e.methodName + " (" + e.fileName + ":" + e.lineNumber + ")"
    }
}
