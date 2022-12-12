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
import com.example.elandmall_kotlin.util.dpToPx

const val SPAN_COUNT = 5
const val ROW_COUNT = 2
const val INDEX_MORE = (SPAN_COUNT * ROW_COUNT) - 1

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

    inner class CategoryListAdapter(private val toggleListener: () -> Unit) :
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
                val data = currentList[adapterPosition]

                Glide.with(root.context)
                    .load(data.imageUrl)
                    .override(30.dpToPx(), 30.dpToPx())
                    .into(cateImg)

                if (adapterPosition == INDEX_MORE && !isExpanded) {
                    // expand icon
                    root.setOnClickListener {
                        toggle.invoke()
                    }

                    cateMore.visibility = View.VISIBLE
                    cateMoreTxt.visibility = View.VISIBLE
                    cateImg.visibility = View.GONE

                } else {
                    // category icons
                    root.setOnClickListener {
                        EventBus.fire(LinkEvent(data.linkUrl))
                    }

                    cateName.text = data.title

                    cateMore.visibility = View.GONE
                    cateMoreTxt.visibility = View.GONE
                    cateImg.visibility = View.VISIBLE
                }
            }
        }
    }
}

