package com.example.elandmall_kotlin.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.BtnCategoryBinding


@SuppressLint("Recycle", "CustomViewStyleable")
class CommonCategoryButtonView @JvmOverloads constructor(
    context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(
    context, attrs, defStyleAttr
) {
    var binding: BtnCategoryBinding = BtnCategoryBinding.inflate(LayoutInflater.from(this.context))

    init {
        addView(binding.root)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CategoryButton)

            val imageSrc = typedArray.getResourceId(R.styleable.CategoryButton_ctg_icon, R.drawable.ic_user)
            binding.icon.setImageResource(imageSrc)
        }
    }

    var title = ""
        set(value) {
            binding.title.text = value
            field = value
        }

    var click = {}
        set(value) {
            binding.root.setOnClickListener {
                value.invoke()
            }
            field = value
        }

    var count: Int? = null
        set(value) {
            if (value != null) {
                binding.countHolder.visibility = View.VISIBLE
                binding.count.text = value.toString()
            }
            field = value
        }
}