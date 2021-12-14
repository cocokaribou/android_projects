package com.pionnet.overpass.util

import android.content.Context
import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * getJsonFileToString
 * - api 없이 테스트시 사용
 * @param filePath : 하드코딩된 .json 파일
 * @param context : 앱 컨텍스트
 * @return String : 문자열화된 json
 */

fun getJsonFileToString(filePath: String, context: Context): String {
    var result = ""
    val assetManager: AssetManager = context.assets
    try {
        val `is` = assetManager.open(filePath!!)
        val isr = InputStreamReader(`is`)
        val reader = BufferedReader(isr)
        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(
                """$line""".trimIndent()
            )
            line = reader.readLine()
        }
        result = buffer.toString()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return result
}