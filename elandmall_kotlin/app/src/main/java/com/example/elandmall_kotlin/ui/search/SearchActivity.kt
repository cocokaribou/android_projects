package com.example.elandmall_kotlin.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_SEARCH_TAB
import com.example.elandmall_kotlin.common.CommonConst.SEARCH_POPULAR
import com.example.elandmall_kotlin.databinding.ActivityIntroBinding
import com.example.elandmall_kotlin.databinding.ActivitySearchBinding
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.LinkEventType
import com.example.elandmall_kotlin.ui.main.MainTabPagerAdapter
import com.example.elandmall_kotlin.util.*
import com.example.elandmall_kotlin.util.CustomTabUtil.draw
import com.example.elandmall_kotlin.util.CustomTabUtil.setTabListener
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception

class SearchActivity : BaseActivity() {
    val viewModel: SearchViewModel by viewModels()
    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    private val mAdapter by lazy { SearchTabPagerAdapter(supportFragmentManager, lifecycle) }
    private val tabList = listOf("인기", "최근", "브랜드")

    var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }

        setContentView(binding.root)

        resolveIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {
        val tab = intent?.getStringExtra(EXTRA_SEARCH_TAB) ?: SEARCH_POPULAR
        currentTab = try {
            tab.toInt()
        } catch (e: Exception) {
            0
        }

        initUI()
        initTopBar()
        initObserve()
    }

    private fun initUI() = with(binding) {
        viewpager.apply {
            adapter = mAdapter
            isUserInputEnabled = false
            offscreenPageLimit = tabList.size
            setCurrentItem(currentTab, false)
        }

        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun initTopBar() = with(binding.topBar) {
        close.setOnClickListener { finish() }
        clear.setOnClickListener { searchInput.setText("") }
        capture.setOnClickListener { EventBus.fire(LinkEvent(LinkEventType.CAPTURE)) }

        search.setOnClickListener { searchInput.clearEditTextFocus() }

        searchInput.apply {
            setOnKeyListener(object : EnterListener() {
                override fun onEnter() { searchInput.clearEditTextFocus() }
            })
            onFocusChangeListener = object : TextFocusListener() {
                override fun onFocusOut() { searchInput.clearEditTextFocus() }
            }
            addTextChangedListener(object : TextInputWatcher() {
                override fun onInputTextChanged() { clear.visibility = if (searchInput.text.isNotEmpty()) View.VISIBLE else View.GONE }
            })
        }
    }

    private fun initObserve() {

    }
}