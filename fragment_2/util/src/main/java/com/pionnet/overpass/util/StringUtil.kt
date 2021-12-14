package com.pionnet.overpass.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 포맷 출력
 * @param format : 날짜 포맷
 * @return String: 포맷된 현재 날짜
 */
fun Date.getFormattedDate(format: String): String {
    return SimpleDateFormat(format).format(this)
}

/**
 * 현재 시간에서 addDate 만큼 계산하기
 * @param format : 계산전 날짜 포맷 (ex: "yyyyMMdd")
 * @param oldDate : 계산전 날짜 String (ex: "20211125") // 입력안할시 현재 날짜 디폴트
 * @param addDate : 계산할 일수 Int
 *
 * @return String : 포맷된 날짜 문자열
 */
fun getAddedDate(format: String, oldDate: String? = null, addDate: Int): String {
    val formatter = SimpleDateFormat(format)
    val cal = Calendar.getInstance()

    if(oldDate != null){
        try { cal.time = formatter.parse(oldDate) }
        catch (e: Exception) {}
    }else{
        cal.time = Date()
    }
    cal.add(Calendar.DATE, addDate)
    return formatter.format(cal.time)
}

/**
 * 현재 시간 반환
 * @return : 포맷된 현재 날짜 문자열
 */
fun getCurrentTime(): String {
    val now: Long = System.currentTimeMillis()
    val date = Date(now)
    val formatter = SimpleDateFormat("yyyy년 M월 dd일 H시 mm분 ss초", Locale("ko", "KR"))
    return formatter.format(date)
}

/**
 * 가격에 천 단위로 반점
 * @param value : 물건 가격
 * @return ex) 포맷된 가격 문자열 value : 10000 -> 출력값 : 10,000
 */
fun getFormattedPrice(value: String): String {
    if (value.isEmpty()) return ""

    val formatter: NumberFormat = DecimalFormat("###,###")
    val amount = value.replace(",".toRegex(), "").toDouble()
    return formatter.format(amount)
}

/**
 * 숫자를 만 단위 문자열로 출력
 * @param value : Long
 * @return ex) 문자열 숫자 문자열 value : 23400 -> 출력값 : 2만+
 */
fun getFormattedProductCnt(value: Long): String {
    return when {
        value < 0 -> ""
        value < 1000 -> value.toString()
        else -> (value/10000).toInt().toString() + "만+"
    }
}