package com.example.elandmall_kotlin.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.elandmall_kotlin.base.BaseFragment
import com.example.elandmall_kotlin.repository.MemDataSource

class MainTabPagerAdapter(fm:FragmentActivity): FragmentStateAdapter(fm) {
    val fragments = hashMapOf<String, BaseFragment>()
    override fun getItemCount(): Int {
        return MemDataSource.mainGnbCache?.data?.gnbList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}