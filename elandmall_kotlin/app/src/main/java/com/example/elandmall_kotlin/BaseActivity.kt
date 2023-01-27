package com.example.elandmall_kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_LINK_EVENT
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_SEARCH_TAB
import com.example.elandmall_kotlin.common.CommonConst.SEARCH_BRAND
import com.example.elandmall_kotlin.common.CommonConst.SEARCH_POPULAR
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
            LinkEventType.SEARCH -> navToSearch(event.data)
            else -> {}
        }
    }
    fun initTopBar(topBar: LayoutTopBarBinding) = with(topBar) {
        menuIc.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LNB))
        }
        searchInput.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH, SEARCH_POPULAR))
        }
    }

    fun initBottomBar(bottomBar: LayoutBottomBarBinding) = with(bottomBar){
        btn1.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LNB))
        }
        btn2.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH, SEARCH_BRAND))
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

    private fun navToSearch(data: String?) {
        if (isNetworkAvailable(this)) {
            val intent = Intent(this, SearchActivity::class.java)
                .putExtra(EXTRA_SEARCH_TAB, data)
            startActivity(intent)
        } else {

        }
    }

    private fun navToSetting() {}
}