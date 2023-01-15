package com.example.elandmall_kotlin.util

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import com.example.elandmall_kotlin.databinding.ViewCustomTabBinding
import com.example.elandmall_kotlin.model.MainGnbResponse
import com.google.android.material.tabs.TabLayout

object CustomTabUtil {
    val tabList = mutableListOf<ViewCustomTabBinding>()

    fun TabLayout.Tab.draw(gnb: MainGnbResponse.GNBData) {
        val customBinding = ViewCustomTabBinding.inflate(LayoutInflater.from(view.context)).apply {
            gnbTitle.text = gnb.menuName
            gnbSubtitle.text = gnb.menuSubtitle
            newIc.visibility = if (gnb.isNew) View.VISIBLE else View.GONE
            newIcMask.visibility = if (gnb.isNew) View.VISIBLE else View.GONE

            if (tabList.isEmpty()) gnbTitle.typeface = Typeface.DEFAULT_BOLD
        }
        customView = customBinding.root
        tabList.add(customBinding)
    }

    private val tabListener: TabLayout.OnTabSelectedListener by lazy {
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val binding = tabList[tab?.position ?: 0]
                binding.gnbTitle.typeface = Typeface.DEFAULT_BOLD
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val binding = tabList[tab?.position ?: 0]
                binding.gnbTitle.typeface = Typeface.DEFAULT
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }
    }

    fun TabLayout.setTabListener() {
        addOnTabSelectedListener(tabListener)
    }
}