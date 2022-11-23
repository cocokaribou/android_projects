package com.example.elandmall_kotlin

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.LinkEventType
import com.example.elandmall_kotlin.ui.intro.IntroActivity

/**
 * BaseActivity
 * activity navigation using [com.example.elandmall_kotlin.ui.EventBus]
 */
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(
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

    protected fun isSavedInstanceState(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            val intent = Intent(this, IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finishAffinity()
            true
        } else {
            false
        }
    }

    open fun onLinkEvent(event: LinkEvent) {
        when (event.type) {
            LinkEventType.DEFAULT -> {
                Toast(this).apply {
                    setText("link event: ${event.url}")
                }.show()
            }
            else -> {}
        }
    }

    fun initBottomBar() {}

    private fun navToHome() {}

    private fun navToWeb() {}

    private fun navToCategory() {}

    private fun navToBrand() {}

    private fun navToCart() {}

    private fun navToSearch() {}

    private fun navToSetting() {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}