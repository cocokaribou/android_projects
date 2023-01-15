package com.example.elandmall_kotlin.ui.category

import android.os.Bundle
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ActivityCategoryBinding

class CategoryActivity : BaseActivity<ActivityCategoryBinding, CategoryViewModel>(R.layout.activity_category) {
    override val viewModel: CategoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_none)
    }

    override fun finish() {
        super.finish()
//        overridePendingTransition(R.anim.slide_none, R.anim.slide_left_out)
    }
}