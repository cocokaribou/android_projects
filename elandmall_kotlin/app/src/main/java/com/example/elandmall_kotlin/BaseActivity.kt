package com.example.elandmall_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.example.elandmall_kotlin.databinding.LayoutBottomBarBinding
import com.example.elandmall_kotlin.databinding.LayoutTopBarBinding
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.LinkEventType
import com.example.elandmall_kotlin.ui.category.LeftMenuActivity
import com.example.elandmall_kotlin.ui.intro.IntroActivity
import com.example.elandmall_kotlin.ui.search.SearchActivity

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
                navToWeb(event.url)
            }
            LinkEventType.CATEGORY -> {
                navToCategory()
            }
            else -> {}
        }
    }
    fun initTopBar(topBar: LayoutTopBarBinding) = with(topBar) {
        logo.root.setOnClickListener {

        }
        menuIc.setOnClickListener {
            navToCategory()
        }
        searchInput.setOnClickListener {
            navToSearch()
        }
    }

    fun initBottomBar(bottomBar: LayoutBottomBarBinding) = with(bottomBar){
        btn1.setOnClickListener {
            navToCategory()
        }
        btn2.setOnClickListener {
            navToSearch()
        }
        home.setOnClickListener {  }
        btn3.setOnClickListener {  }
        btn4.setOnClickListener {  }
    }

    private fun navToHome() {}

    private fun navToWeb(url: String?) {
        Toast(this).apply {
            setText("link event: $url")
        }.show()
    }

    private fun navToCategory() {
        val intent = Intent(this, LeftMenuActivity::class.java)

        startActivity(intent)
    }

    private fun navToCart() {}

    private fun navToSearch() {
        val intent = Intent(this, SearchActivity::class.java)

        startActivity(intent)
    }

    private fun navToSetting() {}


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}