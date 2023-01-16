package com.example.elandmall_kotlin.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.media.metrics.Event
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.BtnCategoryBinding
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.EventBus.fire
import com.example.elandmall_kotlin.ui.LinkEvent


@SuppressLint("Recycle", "CustomViewStyleable")
class CategoryButton @JvmOverloads constructor(
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