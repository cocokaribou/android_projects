package com.example.elandmall_kotlin.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.BR

abstract class BaseActivity<B : ViewDataBinding, VM: ViewModel>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    val binding: B
        get() = _binding!!
    private var _binding: B? = null

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)

        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, viewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}