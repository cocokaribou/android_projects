package com.example.elandmall_kotlin.ui.main.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewEkidsRecommendCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewEkidsRecommendCategoryItemBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.ekids.ChangeCategoryCallback
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

var weeklyBestSelected = 0
var newArrivalSelected = 0
var viewType = ""

class EKidsRecommendCategoryViewHolder(private val binding: ViewEkidsRecommendCategoryBinding) : BaseViewHolder(binding.root) {
    private val mDecoration by lazy { HorizontalSpacingItemDecoration(6.dpToPx(), 12.dpToPx(), true) }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsRecommendCategoryData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.EKidsRecommendCategoryData) = with(binding) {
        viewType = data.viewType
        val mWeeklyAdapter = CategoryAdapter(data.changeCategory).apply { submitList(data.categoryList) }
        val mNewAdapter = CategoryAdapter(data.changeCategory).apply { submitList(data.categoryList) }

        if ("weeklyBest".equals(data.viewType, true)) {
            weeklyBestSelected = data.cateSelected
            list.adapter = mWeeklyAdapter
            (list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(weeklyBestSelected, 0)
        } else {
            newArrivalSelected = data.cateSelected
            list.adapter = mNewAdapter
            (list.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(newArrivalSelected, 0)
        }
        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(mDecoration)
        }
    }

    inner class CategoryAdapter(val callback: ChangeCategoryCallback) : ListAdapter<String, CategoryAdapter.CategoryHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
            return CategoryHolder(
                ViewEkidsRecommendCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            if ("weeklyBest".equals(viewType, true)) {
                holder.onBind(weeklyBestSelected, callback)
            } else {
                holder.onBind(newArrivalSelected, callback)
            }
        }

        inner class CategoryHolder(val binding: ViewEkidsRecommendCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind(selected: Int, changeCategory: ChangeCategoryCallback) = with(binding) {
                title.apply {
                    text = currentList[adapterPosition]
                    if (selected == adapterPosition) {
                        setTextColor(Color.parseColor("#ffffff"))
                        setBackgroundResource(R.drawable.background_category_selected)
                    } else {
                        setTextColor(Color.parseColor("#2b2b2b"))
                        setBackgroundResource(R.drawable.background_category_default)
                    }
                }
                root.setOnClickListener {
                    if ("weeklyBest".equals(viewType, true)) {
                        weeklyBestSelected = adapterPosition
                        notifyDataSetChanged()

                        changeCategory(weeklyBestSelected)

                    } else {
                        newArrivalSelected = adapterPosition
                        notifyDataSetChanged()

                        changeCategory(newArrivalSelected)
                    }
                }
            }
        }
    }
}