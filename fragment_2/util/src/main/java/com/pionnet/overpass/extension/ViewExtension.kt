package com.pionnet.overpass.extension

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout

/**
 * setLayoutWeight
 * - LinearLayout에서 weight 값 적용
 * @param weight : 가중치
 */
fun View.setLayoutWeight(weight: Float) {
    val lp = layoutParams as LinearLayout.LayoutParams
    lp.weight = weight
    layoutParams = lp
}

/**
 * setMargin
 * - View의 상, 하, 좌, 우 마진
 */
fun View.setMargin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

/**
 * setDynamicHeight
 * - View 높이값 코드로 지정
 * @param value : height (dp)
 */
fun View.setDynamicHeight(value: Float) {
    val lp = layoutParams
    lp.height = dpToPx(value)
    layoutParams = lp
}

/**
 * setDynamicWidth
 * - View 너비값 코드로 지정
 * @param value : width (dp)
 */
fun View.setDynamicWidth(value: Float) {
    val lp = layoutParams
    lp.width = dpToPx(value)
    layoutParams = lp
}

/**
 * dpToPx
 * - dp -> px  코틀린 ViewUtil 에 존재함
 */
fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()


/**
 * expand
 * - View 확장
 */
fun View.expand() {
    this.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = this.measuredHeight

    this.layoutParams.height = 1
    this.visibility = View.VISIBLE

    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height = if (interpolatedTime == 1f)
                LinearLayout.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            this@expand.requestLayout()

        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (targetHeight / this.context.resources.displayMetrics.density).toInt().toLong()
    this.startAnimation(a)
}

/**
 * collapse
 * - 확장된 View 축소
 */
fun View.collapse() {
    val initialHeight: Int = this.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                this@collapse.visibility = View.GONE
            } else {
                this@collapse.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapse.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (initialHeight / this.context.resources.displayMetrics.density).toInt().toLong()
    this.startAnimation(a)
}