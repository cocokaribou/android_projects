package com.pionnet.overpass.extension

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
 * 현재 시간에서 addDate 만큼 계산하기
 * @param format : 데이터 형태
 * @param addDate : 더할 날짜(1일)
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
 * @param size : "원" 있을 때 원은 글자크기 조절 가능
 * @param isExist : "원" 포함유무
 */
fun TextView.setPriceStroke(size: Int, isExist: Boolean) {
    val resultText: String = this.text.toString()
    val ssb = SpannableStringBuilder(resultText)
    val start = resultText.indexOf("원")
    if (start >= 0) {
        ssb.setSpan(AbsoluteSizeSpan(size, true), start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    if (isExist) {
        ssb.setSpan(StrikethroughSpan(), 0, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    } else {
        if (start >= 0) {
            ssb.setSpan(StrikethroughSpan(), 0, ssb.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            ssb.setSpan(StrikethroughSpan(), 0, ssb.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

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

/**
 * 텍스트 아래 밑줄 긋기
 *
 */
fun TextView.setUnderLine() {
    this.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}

/**
 * 왼쪽 마진 주기
 * @param leftMargin_dp : 왼쪽 마진 값
 */
fun TextView.setDynamicLeftMargin(leftMargin_dp: Int) {
    val context = this.context
    (this.layoutParams as ConstraintLayout.LayoutParams).apply {
        this.leftMargin = leftMargin_dp.dpToPx(context)
    }
}