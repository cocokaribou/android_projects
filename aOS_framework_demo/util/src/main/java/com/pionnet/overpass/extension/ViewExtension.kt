package com.pionnet.overpass.extension

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
 * 이미지 가져오기
 * @param url : 이미지 URL
 */
fun ImageView.loadImagePreLoad(url: String) {
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
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
 * 스플래시 이미지 가져오기
 * @param url : 이미지 URL
 */
fun preLoadSplash(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .preload();
}

/**
 * 스플래시 이미지 가져오기
 * @param leftMargin_dp : 왼쪽 마진 값
 */
fun TextView.setDynamicLeftMargin(leftMargin_dp: Int) {
    val context = this.context
    (this.layoutParams as ConstraintLayout.LayoutParams).apply {
        this.leftMargin = leftMargin_dp.dpToPx(context)
    }
}

/**
 * linearlayout에서 weight 값 적용
 * @param weight : 가중치
 */
fun View.setLayoutWeight(weight: Float) {
    val lp = layoutParams as LinearLayout.LayoutParams
    lp?.let {
        lp.weight = weight
        layoutParams = lp
    }
}


