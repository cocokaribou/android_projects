package com.youngin.lunch.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.BaseRequestOptions


enum class Canvas {
    SQUARE,
    PORTRAIT,
    LANDSCAPE
}

fun ImageView.isSquareOrNot(): Canvas {
    return if (this.width == this.height) {
        Canvas.SQUARE
    } else if (this.width < this.height) {
        Canvas.PORTRAIT
    } else {
        Canvas.LANDSCAPE
    }
}