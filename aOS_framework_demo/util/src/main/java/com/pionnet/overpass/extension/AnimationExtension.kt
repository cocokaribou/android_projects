package com.pionnet.overpass.extension

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout

/**
 * 레이아웃 확장
 *
 */
fun expand(v: View) {
    v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    val targetHeight: Int = v.measuredHeight

    v.layoutParams.height = 1
    v.visibility = View.VISIBLE

    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            v.layoutParams.height = if (interpolatedTime == 1f)
                LinearLayout.LayoutParams.WRAP_CONTENT
            else
                (targetHeight * interpolatedTime).toInt()
            v.requestLayout()

        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}

/**
 * 레이아웃 확장된 것 닫기
 *
 */
fun collapse(v: View) {
    val initialHeight : Int = v.measuredHeight
    val a : Animation = object : Animation(){
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f){
                v.visibility = View.GONE
            }else{
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}