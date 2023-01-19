package com.example.elandmall_kotlin

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_LINK_EVENT
import com.example.elandmall_kotlin.databinding.LayoutBottomBarBinding
import com.example.elandmall_kotlin.databinding.LayoutTopBarBinding
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.LinkEventType
import com.example.elandmall_kotlin.ui.letfmenu.LeftMenuActivity
import com.example.elandmall_kotlin.ui.intro.IntroActivity
import com.example.elandmall_kotlin.ui.search.SearchActivity
import com.example.elandmall_kotlin.util.isNetworkAvailable

/**
 * BaseActivity
 * activity navigation using [com.example.elandmall_kotlin.ui.EventBus]
 */
open class BaseActivity : AppCompatActivity() {

    private val resultNavToLNB = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.resultCode) {
            RESULT_OK -> {
                it.data?.let { intent ->
                    intent.getSerializableExtra(EXTRA_LINK_EVENT)?.let { extra ->
                        onLinkEvent(extra as LinkEvent)
                    }
                }
            }
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

    protected open fun onLinkEvent(event: LinkEvent) {
        when (event.type) {
            LinkEventType.LNB -> navToLNB()
            LinkEventType.DEFAULT -> navToWeb(event.url)
            LinkEventType.SEARCH -> navToSearch()
            else -> {}
        }
    }
    fun initTopBar(topBar: LayoutTopBarBinding) = with(topBar) {
        menuIc.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LNB))
        }
        searchInput.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
        }
    }

    fun initBottomBar(bottomBar: LayoutBottomBarBinding) = with(bottomBar){
        btn1.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LNB))
        }
        btn2.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH))
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

    private fun navToLNB() {
        if (isNetworkAvailable(this)) {
            resultNavToLNB.launch(Intent(this, LeftMenuActivity::class.java))
        } else {

        }
    }

    private fun navToCart() {}

    private fun navToSearch() {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, SearchActivity::class.java))
        } else {

        }
    }

    private fun navToSetting() {}
}