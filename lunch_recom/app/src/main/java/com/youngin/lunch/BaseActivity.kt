package com.youngin.lunch

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {
    val binding: B
        get() = _binding!!
    private var _binding: B? = null

    abstract val viewModel: VM
    lateinit var service: BaseApiService

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResId)
        with(binding) {
            lifecycleOwner = this@BaseActivity
            setVariable(BR.vm, viewModel)
        }

        initView()
        initObserve()

        service = BaseApiService.getApiService()
    }

    abstract fun initView()

    abstract fun initObserve()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}