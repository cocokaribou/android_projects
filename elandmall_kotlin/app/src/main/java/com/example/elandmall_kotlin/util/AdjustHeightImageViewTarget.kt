package com.example.elandmall_kotlin.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.math.roundToInt

open class AdjustHeightImageViewTarget(imageView: ImageView): ImageViewTarget<Drawable>(imageView) {
    override fun setResource(resource: Drawable?) {
        setDrawable(resource)
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        super.onResourceReady(resource, transition)

        val width = resource.intrinsicWidth
        val height = resource.intrinsicHeight

        val ratio = height.toFloat() / width.toFloat()
        val newHeight = view.width * ratio

        view.layoutParams.height = newHeight.roundToInt()
        view.requestLayout()
    }
}