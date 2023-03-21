package com.example.elandmall_kotlin.util

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.elandmall_kotlin.BaseApplication

fun String.useNonBreakingSpace() = this.replace(' ', '\u00A0')

fun String.censoredString(visibleCharCount: Int): String {
    var hiddenString = ""
    this.forEachIndexed { i, c ->
        val char = if (i < visibleCharCount) c else '*'
        hiddenString += char
    }
    return hiddenString
}
fun getChangedIndex(origin: String, changed: String): Pair<Int, Int> {
    if (origin.isEmpty()) return Pair(0, 0)

    var start = origin.indexOf(changed, 0)
    var end = start + changed.length
    if (start == -1) {
        start = 0
        end = origin.length
    }
    return Pair(start, end)
}

fun getSpannedBoldText(origin: String, changed: String): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        StyleSpan(Typeface.BOLD),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return sb
}

fun getSpannedSizeText(
    origin: String,
    changed: String,
    sizeDp: Int,
    bold: Boolean = false
): Spannable {

    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        AbsoluteSizeSpan(sizeDp, true),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun getSpannedColorText(origin: String, changed: String, color: Int, bold: Boolean = false): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        ForegroundColorSpan(color),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

fun getSpannedColorSizeText(origin: String, changed: String, color: Int, sizeDp: Int, bold: Boolean = false): Spannable {
    val sb = SpannableStringBuilder(origin)
    val pair = getChangedIndex(origin, changed)

    sb.setSpan(
        ForegroundColorSpan(color),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    sb.setSpan(
        AbsoluteSizeSpan(sizeDp, true),
        pair.first,
        pair.second,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold) {
        sb.setSpan(
            StyleSpan(Typeface.BOLD),
            pair.first,
            pair.second,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sb
}

// 키보드 내리기
fun EditText.clearEditTextFocus() {
    val inputManager = BaseApplication.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)

    this.clearFocus()
}


// 엔터키 리스너 클래스
abstract class EnterListener : View.OnKeyListener {
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            onEnter()
            return true
        }
        return false
    }

    abstract fun onEnter()
}

// 포커스아웃 리스너 클래스
abstract class TextFocusListener : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        v?.let { view ->
            if (!view.isFocused) onFocusOut()
        }
    }

    abstract fun onFocusOut()
}

// 텍스트 와처 클래스
abstract class TextInputWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        onInputTextChanged()
    }

    abstract fun onInputTextChanged()
}
