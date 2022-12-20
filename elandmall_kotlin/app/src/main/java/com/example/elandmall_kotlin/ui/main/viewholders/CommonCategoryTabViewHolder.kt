package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewCommonCategoryTabBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMdCateItemBinding
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder

class CommonCategoryTabViewHolder(private val binding: ViewCommonCategoryTabBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
    }

    inner class CategoryAdapter(private val tabSelector: (Int) -> Unit) :
        ListAdapter<HomeResponse.HomeMd.HomeMdCat, CategoryAdapter.MdCateViewHolder>(object :
            DiffUtil.ItemCallback<HomeResponse.HomeMd.HomeMdCat>() {
            override fun areItemsTheSame(oldItem: HomeResponse.HomeMd.HomeMdCat, newItem: HomeResponse.HomeMd.HomeMdCat): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeResponse.HomeMd.HomeMdCat, newItem: HomeResponse.HomeMd.HomeMdCat): Boolean {
                return oldItem.menuTitle == newItem.menuTitle
            }

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
            holder.onBind(tabSelector)
        }

        inner class MdCateViewHolder(private val binding: ViewHomeMdCateItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind(tabSelector: (Int) -> Unit) = with(binding) {
                selectIndicator.isSelected = adapterPosition == selectedTab

                val data = currentList[adapterPosition]
                Glide.with(root.context)
                    .load("http:" + data.imageUrl)
                    .into(cateImg)

                cateName.text = data.menuTitle
                root.setOnClickListener {
                    selectIndicator.isSelected = !selectIndicator.isSelected
                    tabSelector(adapterPosition)
                }
            }
        }
    }
}