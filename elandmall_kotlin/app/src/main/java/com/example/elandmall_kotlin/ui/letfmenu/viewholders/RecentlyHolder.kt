package com.example.elandmall_kotlin.ui.letfmenu.viewholders

import android.view.View
import com.example.elandmall_kotlin.databinding.ViewLnbRecentlyBinding
import com.example.elandmall_kotlin.ui.letfmenu.LeftMenuBaseViewHolder
import com.example.elandmall_kotlin.util.Logger

class RecentlyHolder(val binding: ViewLnbRecentlyBinding): LeftMenuBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            initUI(item)
        }
    }

    private fun initUI(item: List<*>) = with(binding){
        if (item.isEmpty()) {
            recentlyEmpty.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            recentlyEmpty.visibility = View.GONE
            list.visibility = View.VISIBLE
        }

        search.setOnClickListener {

        }
    }
}