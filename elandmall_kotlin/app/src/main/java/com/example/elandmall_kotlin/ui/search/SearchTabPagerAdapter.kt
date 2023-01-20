package com.example.elandmall_kotlin.ui.search

import android.provider.SearchRecentSuggestions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.elandmall_kotlin.ui.search.tabs.brand.SearchBrandFragment
import com.example.elandmall_kotlin.ui.search.tabs.popular.SearchPopularFragment
import com.example.elandmall_kotlin.ui.search.tabs.recently.SearchRecentlyFragment

class SearchTabPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fm, lifeCycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 ->SearchPopularFragment()
            1 -> SearchRecentlyFragment()
            else -> SearchBrandFragment()
        }
    }
}