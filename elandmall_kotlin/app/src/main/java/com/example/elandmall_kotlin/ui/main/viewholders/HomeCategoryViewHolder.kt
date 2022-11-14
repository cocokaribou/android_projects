package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryItemBinding
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData

val SPAN_COUNT = 5
val CLICK_EXPAND = (SPAN_COUNT * 2) - 1

class HomeCategoryViewHolder(private val binding: ViewHomeCategoryBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { CategoryListAdapter() }
    private val mLayoutManager by lazy { GridLayoutManager(binding.root.context, SPAN_COUNT) }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeCategoryIconData)?.let {
            initView(it)
        }
    }

    fun initView(data: ModuleData.HomeCategoryIconData) = with(binding) {
        categoryList.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }

        mAdapter.submitList(data.homeCategoryIconData.subList(0, 9))
    }
}

class CategoryListAdapter : ListAdapter<HomeResponse.HomeCategoryIcon, CategoryListAdapter.CategoryItemViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<HomeResponse.HomeCategoryIcon>() {
            override fun areItemsTheSame(oldItem: HomeResponse.HomeCategoryIcon, newItem: HomeResponse.HomeCategoryIcon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeResponse.HomeCategoryIcon, newItem: HomeResponse.HomeCategoryIcon): Boolean {
                return oldItem.categoryNo == newItem.categoryNo
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        return CategoryItemViewHolder(
            ViewHomeCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class CategoryItemViewHolder(private val binding: ViewHomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeResponse.HomeCategoryIcon) = with(binding) {
            if (adapterPosition != CLICK_EXPAND) {
                // category icons
                root.setOnClickListener {
                    EventBus.fire(LinkEvent(data.linkUrl))
                }

                cateName.text = data.title

                Glide.with(binding.root.context)
                    .load(data.imageUrl)
                    .into(cateImg)

            } else {
                // expand icon
                root.setOnClickListener {
                    TODO("expand list")
                }

                cateName.text = "더보기"

                Glide.with(binding.root.context)
                    .load(R.mipmap.ic_launcher)
                    .into(cateImg)
            }

        }
    }

}