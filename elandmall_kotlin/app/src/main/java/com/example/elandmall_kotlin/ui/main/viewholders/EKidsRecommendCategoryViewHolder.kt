package com.example.elandmall_kotlin.ui.main.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewEkidsRecommendCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewEkidsRecommendCategoryItemBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var weeklyBestSelected = 0
var newArrivalSelected = 0
var viewType = ""

class EKidsRecommendCategoryViewHolder(private val binding: ViewEkidsRecommendCategoryBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsRecommendCategoryData)?.let {
            Logger.v("그린다~ ${it.viewType}")
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.EKidsRecommendCategoryData) = with(binding) {
        viewType = data.viewType
        val mWeeklyAdapter = CategoryAdapter().apply { submitList(data.categoryList) }
        val mNewAdapter = CategoryAdapter().apply { submitList(data.categoryList) }

        Logger.v("지금 타입 ${data.viewType}")
        if ("weeklyBest".equals(data.viewType, true)) {
            weeklyBestSelected = data.cateSelected
            list.adapter = mWeeklyAdapter
        } else {
            newArrivalSelected = data.cateSelected
            list.adapter = mNewAdapter
        }
    }

    inner class CategoryAdapter : ListAdapter<String, CategoryAdapter.CategoryHolder>(object : DiffUtil.ItemCallback<String>() {
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
                holder.onBind(weeklyBestSelected)
            } else {
                holder.onBind(newArrivalSelected)
            }
        }

        inner class CategoryHolder(val binding: ViewEkidsRecommendCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind(selected: Int) = with(binding) {
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

                        EventBus.fire(ViewHolderEvent(ViewHolderEventType.CATEGORY_SCROLL1, TabType.EKIDS, weeklyBestSelected))

                    } else {
                        newArrivalSelected = adapterPosition
                        notifyDataSetChanged()

                        EventBus.fire(ViewHolderEvent(ViewHolderEventType.CATEGORY_SCROLL2, TabType.EKIDS, newArrivalSelected))
                    }
                }
            }
        }
    }
}