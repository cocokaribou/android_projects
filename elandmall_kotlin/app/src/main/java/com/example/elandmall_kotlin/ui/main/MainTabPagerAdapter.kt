package com.example.elandmall_kotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.elandmall_kotlin.base.BaseModuleFragment
import com.example.elandmall_kotlin.common.CommonConst.HOME_MENU_CD
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.tabs.HomeModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.WebModulesFragment
import com.example.elandmall_kotlin.util.Logger

class MainTabPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    // TODO infinite horizontal scroll

    private val fragments = hashMapOf<String?, BaseModuleFragment>()
    val data = MemDataSource.mainGnbCache?.data?.gnbList

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    // TODO filter gnb list
    override fun createFragment(position: Int): Fragment {
        val gnb = data?.getOrNull(position)
        var fragment =
            when (gnb?.menuCd) {
                HOME_MENU_CD -> {
                    HomeModuleFragment.create(
                        tabName = gnb.menuName ?: "",
                        apiUrl = gnb.apiUrl ?: ""
                    )
                }
                else -> {
                    WebModulesFragment.create(
                        tabName = gnb?.menuName ?: "",
                        apiUrl = gnb?.apiUrl ?: ""
                    )
                }
            }
        fragments[gnb?.menuCd] = fragment
        return fragment
    }
}
