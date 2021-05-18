package com.pionnet.overpass.extension

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 포멧 출력
 */
fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(this)
}

/**
 * 현재 시간에서 addDate 만큼 더하기
 * @param format : 데이터 형태
 * @param addDate : 더할 날짜
 */
fun getAddDateString(format: String, addDate: Int): String {
    val simpleDate = SimpleDateFormat(format)
    val cal = Calendar.getInstance()
    cal.add(Calendar.DATE, addDate)
    return simpleDate.format(cal.time)
}

/**
 *  현재 시간 반환
 */
fun getCurrentTime(): String {
    val now: Long = System.currentTimeMillis()
    val date = Date(now)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
    return dateFormat.format(date)
}

/**
 * dp -> px
 */
fun Int.dpToPx(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

/**
 * text bold 처리
 */
fun TextView.setBoldText() {
    val ss1 = SpannableString(this.text.toString())
    ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0)
    this.text = ss1
}

/**
 * text 가운데 줄 처리
 */
fun TextView.setPriceStroke() {
    val resultText: String = this.text.toString()
    val ssb = SpannableStringBuilder(resultText)
    val start = resultText.indexOf("원")
    if (start >= 0) {
        ssb.setSpan(AbsoluteSizeSpan(10, true), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    ssb.setSpan(StrikethroughSpan(), 0, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = ssb
}

/**
 * 가격 포멧
 * @param value : 물건 가격
 * @return ex) value : 10000 -> 출력값 : 10,000
 */
fun priceFormat(value: String): String {
    if (value.isNullOrEmpty()) return ""
    val formatter: NumberFormat = DecimalFormat("###,###")
    val amount = value.replace(",".toRegex(), "").toDouble()
    return formatter.format(amount)
}

/**
 * 상품 갯수 출력 (10000개 기준)
 * @param value : 상품 갯수
 */
fun productCnt(value: Long): String {
    if (value < 0) return ""
    if (value < 10000) return value.toString()

    val cnt = (value / 10000).toInt()
    return cnt.toString() + "만+"
}