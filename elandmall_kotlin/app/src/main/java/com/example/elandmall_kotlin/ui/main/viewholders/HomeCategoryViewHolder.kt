package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryItemBinding
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.viewholders.HomeCategoryViewHolder.Companion.isExpanded
import com.example.elandmall_kotlin.util.GridSideSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger

val SPAN_COUNT = 5
val ROW_COUNT = 2
val INDEX_MORE = (SPAN_COUNT * ROW_COUNT) - 1

class HomeCategoryViewHolder(private val binding: ViewHomeCategoryBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { CategoryListAdapter(::toggleExpand) }
    lateinit var itemDecoration: GridSideSpacingItemDecoration

    companion object {
        var isExpanded = false
    }

    var list = listOf<HomeResponse.HomeCategoryIcon>()
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeCategoryIconData)?.let {
            list = it.homeCategoryIconData
        }

        initView()
    }

    private fun initView() = with(binding) {
        categoryList.adapter = mAdapter

        if (!::itemDecoration.isInitialized) {
            itemDecoration = GridSideSpacingItemDecoration(SPAN_COUNT, 45)
            categoryList.addItemDecoration(itemDecoration)
        }
        more.setOnClickListener {
            toggleExpand()
        }

        mAdapter.submitList(list.subList(0, SPAN_COUNT * ROW_COUNT))
    }

    private fun toggleExpand() {
        if (isExpanded) {
            // collapse
            isExpanded = false
            binding.more.visibility = View.GONE
            mAdapter.submitList(list.subList(0, SPAN_COUNT * ROW_COUNT))
        } else {
            // expand
            isExpanded = true
            binding.more.visibility = View.VISIBLE
            mAdapter.submitList(list)
        }
        mAdapter.notifyItemChanged(INDEX_MORE)
    }

    inner class CategoryListAdapter(val toggleListener: () -> Unit) :
        ListAdapter<HomeResponse.HomeCategoryIcon, CategoryListAdapter.CategoryItemViewHolder>(object :
            DiffUtil.ItemCallback<HomeResponse.HomeCategoryIcon>() {
            override fun areItemsTheSame(oldItem: HomeResponse.HomeCategoryIcon, newItem: HomeResponse.HomeCategoryIcon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeResponse.HomeCategoryIcon, newItem: HomeResponse.HomeCategoryIcon): Boolean {
                return oldItem.categoryNo == newItem.categoryNo
            }

        }) {

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
            holder.onBind(toggleListener)
        }

        inner class CategoryItemViewHolder(private val binding: ViewHomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind(toggle: () -> Unit) = with(binding) {
                if (adapterPosition == INDEX_MORE && !isExpanded) {
                    // expand icon
                    root.setOnClickListener {
                        toggle.invoke()
                    }

                    cateName.text = "더보기"

                    Glide.with(itemView.context)
                        .load(R.drawable.home_category_more)
                        .into(cateImg)

                } else {
                    // category icons
                    val data = currentList[adapterPosition]
                    root.setOnClickListener {
                        EventBus.fire(LinkEvent(data.linkUrl))
                    }

                    cateName.text = data.title

                    Glide.with(itemView.context)
                        .load(data.imageUrl)
                        .into(cateImg)
                }
            }
        }
    }
}

