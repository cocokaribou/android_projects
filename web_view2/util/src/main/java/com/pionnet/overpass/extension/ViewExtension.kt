package com.pionnet.overpass.extension

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * 이미지 가져오기(사이즈 고정)
 * @param url : 이미지 URL
 */
fun ImageView.loadImage(url: String) {
    Glide.with(this)
            .load(url)
            .override(500, 500)
            .into(this)
}

/**
 * 이미지 가져오기(사이즈 동적)
 * @param url : 이미지 URL
 * @param width : 가로 사이즈
 * @param height : 세로 사이즈
 */
fun ImageView.loadImageWithScale(url: String, width: Int, height: Int) {
    Glide.with(this)
            .load(url)
            .override(width, height)
            .into(this)
}

/**
 * 이미지 가져오기
 * @param url : 이미지 URL
 */
fun ImageView.loadImageWithOriginal(url: String) {
    Glide.with(this)
            .load(url)
            .into(this)
}

/**
 * 이미지 가져오기 (이미지 캐시해서 로드 속도 빠르게 하기)
 * @param url : 이미지 URL
 */
fun ImageView.loadImagePreLoad(url: String) {
    Log.i("test","time = ${getCurrentTime()}")
    Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                ): Boolean {
                    Log.i("test", "preload finish time = ${getCurrentTime()}")
                    return false
                }

                override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                ): Boolean {
                    Log.i("test", "preload finish time = ${getCurrentTime()}")
                    return false
                }
            })
            .into(this)
}

/**
 * 이미지 가져오기(원형으로 만들기)
 * @param url : 이미지 URL
 */
fun ImageView.loadImageCircle(url: String) {
    Glide.with(this)
            .load(url)
            .centerCrop()
            .circleCrop()
            .transition(DrawableTransitionOptions().crossFade())
            .override(100, 100)
            .into(this)
}

/**
 * 이미지 가져오기(원형으로 만들기)
 * @param url : 이미지 URL
 * @param width : 가로 사이즈
 * @param height : 세로 사이즈
 */
fun ImageView.loadImageCircle(url: String, width: Int, height: Int) {
    Glide.with(this)
            .load(url)
            .centerCrop()
            .circleCrop()
            .transition(DrawableTransitionOptions().crossFade())
            .override(width, height)
            .into(this)
}

/**
 * 스플래시 이미지 가져오기
 * @param url : 이미지 URL
 */
fun preLoadSplash(context: Context, url: String) {
    Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .preload();
}

/**
 * linearlayout에서 weight 값 적용 (전체 사이즈 : weight = 1로 고정)
 * @param weight : 가중치
 */
fun View.setLayoutWeight(weight: Float) {
    val lp = layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.weight = weight
        layoutParams = lp
    }
}

/**
 * Horizontal 에서 전체 1로 놓고 위치 설정
 *
 */
fun ConstraintLayout.setHorizontalBias(
        @IdRes targetViewId: Int,
        bias: Float
) {
    val constraintSet = ConstraintSet()
    constraintSet.clone(this)
    constraintSet.setHorizontalBias(targetViewId, bias)
    constraintSet.applyTo(this)
}

/**
 * 상, 하, 좌, 우 마진
 */
fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
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
 * 높이값 지정하기
 */
fun View.setDynamicHeight(value: Float) {
    val lp = layoutParams
    lp?.let {
        lp.height = dpToPx(value)
        layoutParams = lp
    }
}

/**
 * 가로값 지정하기
 */
fun View.setDynamicWidth(value: Float) {
    val lp = layoutParams
    lp?.let {
        lp.width = dpToPx(value)
        layoutParams = lp
    }
}