package com.example.elandmall_kotlin

import android.os.Bundle
import androidx.activity.viewModels
import com.example.elandmall_kotlin.databinding.ActivityMainBinding
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.MainTabPagerAdapter
import com.example.elandmall_kotlin.ui.main.MainViewModel
import com.example.elandmall_kotlin.util.CustomTabUtil.draw
import com.example.elandmall_kotlin.util.CustomTabUtil.setTabListener
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

/**
 * MainActivity
 * - init tab pager
 * - handle landing intent
 */
class MainActivity : BaseActivity() {
    val viewModel : MainViewModel by viewModels()
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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

        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() {
        initGNB()
        initTopBar(binding.topBar)
        initBottomBar(binding.bottomBar)
    }

    private fun initGNB() = with(binding) {
        val gnbList = MemDataSource.mainGnbCache?.data?.gnbList ?: return

        viewpager.apply {
            adapter = mAdapter
            isUserInputEnabled = true
            offscreenPageLimit = gnbList.size
            setCurrentItem(0, false)
        }

        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.draw(gnbList[position])
        }.attach()

        tabs.setTabListener()
    }

    private fun initObserve() {
        EventBus.linkEvent.observe(this) {
            it.getIfNotHandled()?.let { event ->
                onLinkEvent(event)
            }
        }
    }

    override fun onLinkEvent(linkEvent: LinkEvent) {
        super.onLinkEvent(linkEvent)
    }
}