package com.pionnet.overpass.extension

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

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
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
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

