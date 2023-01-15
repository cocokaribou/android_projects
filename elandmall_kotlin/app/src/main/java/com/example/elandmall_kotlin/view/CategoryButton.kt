package com.example.elandmall_kotlin.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.BtnCategoryBinding


@SuppressLint("Recycle", "CustomViewStyleable")
class CategoryButton @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(
    context, attrs, defStyleAttr
) {
    init {
        val binding = BtnCategoryBinding.inflate(LayoutInflater.from(this.context))
        addView(binding.root)

        attrs?.let {
            val typedArray =context.obtainStyledAttributes(it, R.styleable.CategoryButton)

            val imageSrc = typedArray.getResourceId(R.styleable.CategoryButton_ctg_icon, R.drawable.ic_user)
            binding.icon.setImageResource(imageSrc)

            val titleText = typedArray.getString(R.styleable.CategoryButton_ctg_title)
            binding.title.text = titleText
        }
    }
}