package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.databinding.ActivityMainBinding
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.EventBus
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * MainActivity
 * - init tab pager
 * - handle landing intent
 */
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(R.layout.activity_main) {
    override val viewModel by viewModels<BaseViewModel>()

    private val mAdapter by lazy { MainTabPagerAdapter(supportFragmentManager, lifecycle) }

    init {
        MemDataSource.mainGnbCache?.data?.gnbList?.let {
            mAdapter.initFragments(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }
        BaseApplication.instance.isAppRunning = true

        initUI()
        initObserve()
    }

    private fun initUI() {
        initGNB()
    }

    private fun initGNB() = with(binding) {
        viewpager.apply {
            adapter = mAdapter
            isUserInputEnabled = true
            setCurrentItem(3, false)
        }

        MemDataSource.mainGnbCache?.data?.gnbList?.let {
            TabLayoutMediator(tabs, viewpager) { tab, position ->
                tab.text = it[position].menuName
            }.attach()
        }

        tabs.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // do something
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun initObserve() {
        EventBus.linkEvent.observe(this) {
            it.getIfNotHandled()?.let { event ->
                onLinkEvent(event)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.instance.clearData()
    }
}