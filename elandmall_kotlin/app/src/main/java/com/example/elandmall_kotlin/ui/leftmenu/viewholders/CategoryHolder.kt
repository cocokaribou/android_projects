package com.example.elandmall_kotlin.ui.leftmenu.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewLnbCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewLnbCategoryItemBinding
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuBaseViewHolder
import com.example.elandmall_kotlin.util.useNonBreakingSpace

class CategoryHolder(val binding: ViewLnbCategoryBinding) : LeftMenuBaseViewHolder(binding.root) {
    private val mAdapter by lazy { CategoryAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let { it ->
            val dataList = it.filterIsInstance<LeftMenuResponse.NavCatCategoryMenu>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<LeftMenuResponse.NavCatCategoryMenu>) = with(binding) {
        mAdapter.submitList(data)
        list.adapter = mAdapter
    }

    inner class CategoryAdapter : ListAdapter<LeftMenuResponse.NavCatCategoryMenu, CategoryHolder>(object :
        DiffUtil.ItemCallback<LeftMenuResponse.NavCatCategoryMenu>() {
        override fun areItemsTheSame(
            oldItem: LeftMenuResponse.NavCatCategoryMenu, newItem: LeftMenuResponse.NavCatCategoryMenu
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: LeftMenuResponse.NavCatCategoryMenu, newItem: LeftMenuResponse.NavCatCategoryMenu
        ): Boolean = oldItem == newItem

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder =
            CategoryHolder(
                ViewLnbCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            holder.onBind(currentList[position])
        }

    }

    inner class CategoryHolder(private val binding: ViewLnbCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: LeftMenuResponse.NavCatCategoryMenu) = with(binding) {
            Glide.with(root.context)
                .load("http:" + data.imageUrl)
                .into(icon)

            title.text = data.menuTitle?.useNonBreakingSpace()

            root.setOnClickListener {
                EventBus.fire(LinkEvent(data.linkUrl))
            }
        }
    }
}