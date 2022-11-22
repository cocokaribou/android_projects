package com.example.elandmall_kotlin.ui.main

import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.elandmall_kotlin.common.CommonConst.HOME_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.STORE_MENU_CD
import com.example.elandmall_kotlin.model.MainGnbResponse
import com.example.elandmall_kotlin.repository.MemDataSource
import com.example.elandmall_kotlin.ui.main.tabs.home.HomeModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.WebModulesFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopModuleFragment
import com.example.elandmall_kotlin.util.Logger

class MainTabPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fm, lifeCycle) {
    // TODO infinite horizontal scroll

    private val fragments = mutableListOf<BaseModuleFragment>()

    override fun getItemCount() = fragments.size

    fun updateFragment() {
        val gnbList = MemDataSource.mainGnbCache?.data?.gnbList ?: return

        gnbList.forEach { gnb ->
            val fragment = when (gnb.menuCd) {
                HOME_MENU_CD -> {
                    HomeModuleFragment.create(
                        tabName = gnb.menuName ?: "",
                        apiUrl = gnb.apiUrl ?: ""
                    )
                }
                STORE_MENU_CD -> {
                    StoreShopModuleFragment.create(
                        tabName = gnb.menuName ?: "",
                        apiUrl = gnb.apiUrl ?: ""
                    )
                }
                else -> {
                    WebModulesFragment.create(
                        tabName = gnb.menuName ?: "",
                        apiUrl = gnb.apiUrl ?: ""
                    )
                }
            }
            fragments.add(fragment)
        }

        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun onViewDetachedFromWindow(holder: FragmentViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}
