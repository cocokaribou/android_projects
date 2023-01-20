package com.example.elandmall_kotlin.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewSearchPopularPlanShopBinding
import com.example.elandmall_kotlin.databinding.ViewSearchPopularRankingBinding
import com.example.elandmall_kotlin.model.SearchModule
import com.example.elandmall_kotlin.model.SearchModuleType
import com.example.elandmall_kotlin.ui.search.viewholders.PopularPlanShopViewHolder
import com.example.elandmall_kotlin.ui.search.viewholders.PopularRankingViewHolder

class SearchBaseModuleAdapter : ListAdapter<SearchModule, SearchBaseViewHolder>(object : DiffUtil.ItemCallback<SearchModule>() {
    override fun areItemsTheSame(oldItem: SearchModule, newItem: SearchModule): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: SearchModule, newItem: SearchModule): Boolean = oldItem.equals(newItem)
}) {
    override fun getItemViewType(position: Int): Int = currentList[position].type.ordinal
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBaseViewHolder {
        return when (SearchModuleType.values()[viewType]) {
            SearchModuleType.POPULAR_RANKING ->
                PopularRankingViewHolder(
                    ViewSearchPopularRankingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.POPULAR_PLAN_SHOP ->
                PopularPlanShopViewHolder(
                    ViewSearchPopularPlanShopBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.RECENTLY_SEARCHED -> {}
            SearchModuleType.RECENTLY_VIEWED -> {}
            SearchModuleType.BRAND_TOP10 -> {}
            SearchModuleType.BRAND_ALPHABETS -> {}
            SearchModuleType.BRAND_LIST -> {}
            else -> {}
        }
    }

    override fun onBindViewHolder(holder: SearchBaseViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }
}

abstract class SearchBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun onBind(item: Any?) {}
}