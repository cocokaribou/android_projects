package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewCommonCategoryTabBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMdCateItemBinding
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.luckydeal.CategoryPayloadCallback
import com.example.elandmall_kotlin.util.Logger

var cateSelected = 0

class CommonCategoryTabViewHolder(private val binding: ViewCommonCategoryTabBinding) : BaseViewHolder(binding.root) {
    lateinit var cateSelector: CategoryPayloadCallback
    val mAdapter by lazy { CategoryAdapter() }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonCategoryTabData)?.let {
            cateSelector = it.changeCategory
            initUI(it.categoryList)
        }
    }

    private fun initUI(data: List<Category>) = with(binding) {
        list.adapter = mAdapter
        mAdapter.submitList(data)
    }

    inner class CategoryAdapter : ListAdapter<Category, CategoryAdapter.MdCateViewHolder>(object :
        DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem.title == newItem.title
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MdCateViewHolder {
            return MdCateViewHolder(
                ViewHomeMdCateItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MdCateViewHolder, position: Int) {
            holder.onBind()
        }

        inner class MdCateViewHolder(private val binding: ViewHomeMdCateItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                selectIndicator.isSelected = adapterPosition == cateSelected

                val data = currentList[adapterPosition]
                Glide.with(root.context)
                    .load("http:" + data.image)
                    .into(cateImg)

                cateName.text = data.title
                root.setOnClickListener {
                    if (adapterPosition != cateSelected) {
                        cateSelected = adapterPosition
                        cateSelector(listOf(data.payload1!!, data.payload2!!))
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}