package com.example.elandmall_kotlin.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.model.ModuleBrandData
import com.example.elandmall_kotlin.model.SearchBrandKeyword
import com.example.elandmall_kotlin.model.SearchModule
import com.example.elandmall_kotlin.model.SearchModuleType
import com.example.elandmall_kotlin.ui.search.viewholders.*
import com.example.elandmall_kotlin.util.Logger

class SearchBaseModuleAdapter : ListAdapter<SearchModule, SearchBaseViewHolder>(object : DiffUtil.ItemCallback<SearchModule>() {
    override fun areItemsTheSame(oldItem: SearchModule, newItem: SearchModule): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: SearchModule, newItem: SearchModule): Boolean {
        return when {
            oldItem.type == SearchModuleType.BRAND_ALPHABETS && newItem.type == SearchModuleType.BRAND_ALPHABETS ->
                (oldItem.data as ModuleBrandData).list?.get(0).equals((newItem.data as ModuleBrandData).list?.get(0))

            oldItem.type == SearchModuleType.BRAND_LIST_TITLE && newItem.type == SearchModuleType.BRAND_LIST_TITLE ->
                (oldItem.data as SearchBrandKeyword.NavBrandKeywordItem).navBrandKeywordTitle == (newItem.data as SearchBrandKeyword.NavBrandKeywordItem).navBrandKeywordTitle

            else -> oldItem.equals(newItem)
        }
    }

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
            SearchModuleType.RECENTLY_SEARCHED ->
                RecentlySearchedViewHolder(
                    ViewSearchRecentlySearchedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.RECENTLY_VIEWED -> {
                RecentlyViewedViewHolder(
                    ViewSearchRecentlyViewedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            SearchModuleType.BRAND_TOP10 ->
                BrandTop10ViewHolder(
                    ViewSearchBrandTop10Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.BRAND_ALPHABETS ->
                BrandAlphabetsViewHolder(
                    ViewSearchBrandAlphabetsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.BRAND_LIST_TITLE ->
                BrandListTitleViewHolder(
                    ViewSearchBrandListTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            SearchModuleType.BRAND_LIST ->
                BrandListViewHolder(
                    ViewSearchBrandListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: SearchBaseViewHolder, position: Int) {
        holder.onBind(currentList[position].data)
    }
}

abstract class SearchBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun onBind(item: Any?) {}
}