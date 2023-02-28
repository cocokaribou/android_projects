package com.example.shared_viewmodel.ui

import android.os.Bundle
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

val fragList = (0 until 10).distinct()

class CommonModuleRecyclerViewAdapter(homeFrag: Fragment) : FragmentStateAdapter(homeFrag) {
    override fun getItemCount() = fragList.size

    override fun createFragment(position: Int): Fragment =
        CommonModuleBaseFragment().apply {
            arguments = Bundle().apply { putInt("fragment_no", position) }
        }

}