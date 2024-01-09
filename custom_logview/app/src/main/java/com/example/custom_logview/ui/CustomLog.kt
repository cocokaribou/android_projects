package com.example.custom_logview.ui

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser.parseString
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

enum class LogType {
    REQUEST,
    RESPONSE,
    D,
    I,
    W,
    E
}

class LogData(
    val type: LogType,
    val url: Any,
    val length: String,
    val method: String,
    val code: Int,
    var body: Any,
    val time: String,
) {
    val isApiLog = type == LogType.RESPONSE || type == LogType.REQUEST
    var isSuccessful = true

    constructor(request: Request) : this(
        type = LogType.REQUEST,
        url = request.url,
        length = if (request.body != null) request.body!!.contentLength().toString() else "0",
        method = request.method,
        code = 0,
        time = "",
        body = "",
    )

    constructor(response: Response) : this(
        type = LogType.RESPONSE,
        url = response.request.url,
        length = if (response.headers["Content-Length"] != null) response.headers["Content-Length"] + "-byte" else "unknown-length",
        method = "",
        code = response.code,
        body = "",
        time = (response.receivedResponseAtMillis - response.sentRequestAtMillis).toString(),
    ) {
        isSuccessful = code == 200
    }

    constructor(string: String, type: LogType) : this(
        type = type,
        url = "",
        length = "",
        method = "",
        code = 0,
        body = string,
        time = "",
    )

    val msg: String
        get() {
            return when (type) {
                LogType.REQUEST -> "--> $method ${(url as HttpUrl).encodedPath} (${length}-byte body)"
                LogType.RESPONSE -> "<-- $code ${(url as HttpUrl).encodedPath} (${time}ms, $length body)"
                else -> "$body"
            }
        }

    val color: Int
        get() {
            return when (type) {
                LogType.REQUEST -> Color.parseColor("#7DC8CA")
                LogType.RESPONSE -> {
                    if (isSuccessful) Color.parseColor("#F3D2AC")
                    else Color.parseColor("#FF5555")
                }
                LogType.D -> Color.parseColor("#4b86e7")
                LogType.I -> Color.parseColor("#02ff02")
                LogType.W -> Color.parseColor("#ffff07")
                else -> Color.parseColor("#ff5555")
            }
        }
}

private val gson by lazy { GsonBuilder().setPrettyPrinting().create() }

object CustomLog {
    private val logList = mutableListOf<LogData>()
    val logLiveData = MutableLiveData<List<LogData>>()

    fun a(request: Request) {
        logList.add(LogData(request).apply {
            val headers = request.headers.joinToString(separator = "\n") { "${it.first}: ${it.second}" }
            val queries = request.url.query?.split("&")?.joinToString(separator = "\n") ?: ""
            val fields = try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                copy.body?.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: Exception) {
                ""
            }

            body = "\n$url\n\n" +
                    "-----Header-----\n$headers\n\n" +
                    "-----Query-----\n$queries\n\n" +
                    "-----Field-----\n$fields"
        })
        logLiveData.postValue(logList.reversed())
    }

    fun a(response: Response) {
        val _body = if (response.peekBody(Long.MAX_VALUE).contentLength() > 0L) {
            try {
                gson.toJson(parseString(response.peekBody(Long.MAX_VALUE).string()))
            } catch (e: Exception) {
                ""
            }
        } else {
            ""
        }
        logList.add(LogData(response).apply {
            body = "\n$url\n\n" +
                    "-----Response-----\n$_body" })
        logLiveData.postValue(logList.reversed())
    }

    fun d(string: String) {
        val msg = getMsgFormat(string, object : Throwable() {})
        logList.add(LogData(msg, LogType.D))
        logLiveData.postValue(logList.reversed())
    }

    fun i(string: String) {
        val msg = getMsgFormat(string, object : Throwable() {})
        logList.add(LogData(msg, LogType.I))
        logLiveData.postValue(logList.reversed())
    }

    fun w(string: String) {
        val msg = getMsgFormat(string, object : Throwable() {})
        logList.add(LogData(msg, LogType.W))
        logLiveData.postValue(logList.reversed())
    }

    fun e(string: String) {
        val msg = getMsgFormat(string, object : Throwable() {})
        logList.add(LogData(msg, LogType.E))
        logLiveData.postValue(logList.reversed())
    }

    fun search(word: String) {
        logLiveData.postValue(mutableListOf())
        val list = logList.filter { it.msg.contains(word) || it.body.toString().contains(word) }
        logLiveData.postValue(list.reversed())
    }

    fun save(context: Context): Boolean {
        val formatter = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault())
        val dateString = formatter.format(Date())

        val logString = logList.joinToString("") { it.msg+"\n\n" }

        val dir = File(context.filesDir, "logs")
        if (!dir.exists()) {
            dir.mkdir()
        }

        return try {
            val file = File(dir, dateString)
            val writer = FileWriter(file)
            writer.append(logString)
            writer.flush()
            writer.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun clear() {
        logList.clear()
        logLiveData.postValue(mutableListOf())
    }


    private fun getMsgFormat(input: String, t: Throwable): String {
        if (t.stackTrace.size < 2) return ""
        val loc = t.stackTrace[1].fileName.split(".")[0] + ":" + t.stackTrace[1].lineNumber
        val time = SimpleDateFormat("[HH:mm:ss.SSS]", Locale.KOREA).format(Date()).toString()

        return "$input\n$loc $time"
    }
}
