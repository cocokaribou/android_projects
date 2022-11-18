package com.example.elandmall_kotlin.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.BaseApplication
import com.example.elandmall_kotlin.databinding.ActivityMainBinding
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.google.android.material.tabs.TabLayoutMediator

/**
 * MainActivity
 * - init tab pager
 * - handle landing intent
 */
class MainActivity: BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()

    val mAdapter by lazy { MainTabPagerAdapter(supportFragmentManager, lifecycle) }

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

    private fun initGNB() = with(binding){
        mAdapter.updateFragment()
        viewpager.apply {
            adapter = mAdapter
            isUserInputEnabled = true
        }
        val gnbData = MemDataSource.mainGnbCache?.data?.gnbList

        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = gnbData?.get(position)?.menuName
        }.attach()
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