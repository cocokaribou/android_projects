package com.example.elandmall_kotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.elandmall_kotlin.common.CommonConst.BEST_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.EKIDS_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.ESHOP_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.HOME_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.LUCKYDEAL_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.PLAN_DETAIL_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.PLAN_MENU_CD
import com.example.elandmall_kotlin.common.CommonConst.STORE_MENU_CD
import com.example.elandmall_kotlin.model.MainGnbResponse
import com.example.elandmall_kotlin.ui.main.tabs.best.BestModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.ekids.EKidsModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.eshop.EShopModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.home.HomeModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.luckydeal.LuckyDealModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.plan.PlanModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.plandetail.PlanDetailModuleFragment
import com.example.elandmall_kotlin.ui.main.tabs.web.WebviewModulesFragment
import com.example.elandmall_kotlin.ui.main.tabs.storeshop.StoreShopModuleFragment

class MainTabPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fm, lifeCycle) {
    // TODO infinite horizontal scroll

    private val fragments = mutableListOf<BaseModuleFragment>()

    override fun getItemCount() = fragments.size

    fun initFragments(gnbList: List<MainGnbResponse.GNBData>) {
        fragments.clear()
        gnbList.forEach { gnb ->
            when (gnb.menuCd) {
                HOME_MENU_CD -> {
                    fragments.add(
                        HomeModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                STORE_MENU_CD -> {
                    fragments.add(
                        StoreShopModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                PLAN_DETAIL_MENU_CD -> {
                    fragments.add(
                        PlanDetailModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }

                EKIDS_MENU_CD -> {
                    fragments.add(
                        EKidsModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }

                LUCKYDEAL_MENU_CD -> {
                    fragments.add(
                        LuckyDealModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                BEST_MENU_CD -> {
                    fragments.add(
                        BestModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                PLAN_MENU_CD -> {
                    fragments.add(
                        PlanModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                ESHOP_MENU_CD -> {
                    fragments.add(
                        EShopModuleFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
                else -> {
                    fragments.add(
                        WebviewModulesFragment.create(
                            tabName = gnb.menuName ?: "",
                            apiUrl = gnb.apiUrl ?: ""
                        )
                    )
                }
            }
        }

        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
