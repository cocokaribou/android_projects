import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.widget.TextView


/**
 * TextView bold 처리
 */
fun TextView.setBoldText() {
    val ss1 = SpannableString(this.text.toString())
    ss1.setSpan(StyleSpan(Typeface.BOLD), 0, ss1.length, 0)
    this.text = ss1
}

/**
 * TextView 가운데 줄 처리
 * @param size : "원" 있을 때 원은 글자크기 조절 가능
 * @param isExist : "원" 포함유무
 */
fun TextView.setCancelStroke(size: Int, isExist: Boolean) {
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
 * TextView 밑줄 긋기
 */
fun TextView.setUnderLine() {
    this.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}
