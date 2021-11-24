import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 날짜 포맷 출력
 * @param format : 날짜 포맷
 */
fun Date.toSimpleString(format: String): String {
    return SimpleDateFormat(format).format(this)
}

/**
 * 현재 시간에서 addDate 만큼 계산하기
 * @param format : 날짜 포맷
 * @param addDate : 계산할 일수
 * @param oldDate : 계산전 날짜
 * @return String : 날짜 문자열
 */
fun getAddDateString(format: String, addDate: Int, oldDate: String? = null): String {
    val simpleDate = SimpleDateFormat(format)
    val cal = Calendar.getInstance()
    oldDate?.let {
        try {
            cal.time = simpleDate.parse(it)
        } catch (e: Exception) {
        }
    }
    cal.add(Calendar.DATE, addDate)
    return simpleDate.format(cal.time)
}

/**
 * 현재 시간 반환
 */
fun getCurrentTime(): String {
    val now: Long = System.currentTimeMillis()
    val date = Date(now)
    val dateFormat = SimpleDateFormat("yyyy년 M월 dd일 H시 mm분 ss초", Locale("ko", "KR"))
    return dateFormat.format(date)
}

/**
 * 가격에 천 단위로 반점
 * @param value : 물건 가격
 * @return ex) value : 10000 -> 출력값 : 10,000
 */
fun priceFormat(value: String): String {
    if (value.isEmpty()) return ""

    val formatter: NumberFormat = DecimalFormat("###,###")
    val amount = value.replace(",".toRegex(), "").toDouble()
    return formatter.format(amount)
}

/**
 * 숫자를 만 단위 문자열로 출력
 * @param value : Long
 * @return ex) value : 23400 -> 출력값 : 2만+
 */
fun productCnt(value: Long): String {
    return when {
        value < 0 -> ""
        value < 1000 -> value.toString()
        else -> (value/10000).toInt().toString() + "만+"

    }

}