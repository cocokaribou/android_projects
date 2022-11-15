package com.example.elandmall_kotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.elandmall_kotlin.common.CommonConst.HOME_MENU_CD
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.tabs.home.HomeModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.WebModulesFragment
import com.example.elandmall_kotlin.util.Logger

class MainTabPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fm, lifeCycle) {
    // TODO infinite horizontal scroll

    private val fragments = hashMapOf<String?, BaseModuleFragment>()
    val data = MemDataSource.mainGnbCache?.data?.gnbList

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        val gnb = data?.getOrNull(position)
        val fragment =
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

    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        Logger.v("onbind tab $position")
    }

    override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Logger.v("onViewDetached ${holder.adapterPosition}")
    }
}
