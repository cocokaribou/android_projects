package com.example.elandmall_kotlin.ui.main.viewholders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewEshopCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewEshopCategoryItemBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.Logger

class EshopCategoryViewHolder(private val binding: ViewEshopCategoryBinding): BaseViewHolder(binding.root) {
    private val mAdapter by lazy { CategoryAdapter() }
    var list = listOf<String>()

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EshopCategoryData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.EshopCategoryData) {
        list = data.categoryList
        binding.list.adapter = mAdapter
    }

    inner class CategoryAdapter: RecyclerView.Adapter<CategoryHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
            return CategoryHolder(
                ViewEshopCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
            holder.onBind(list[position])
        }

        override fun getItemCount() = list.size

    }
    var selectedPos = 0
    inner class CategoryHolder(private val binding: ViewEshopCategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(title: String) {
            binding.title.text = title

            binding.divider.visibility = if (adapterPosition == list.size-1) View.GONE else View.VISIBLE

            if (adapterPosition == selectedPos) {
                binding.title.setTextColor(Color.parseColor("#c9000b"))
                binding.selector.isSelected = true
            } else {
                binding.title.setTextColor(Color.parseColor("#2b2b2b"))
                binding.selector.isSelected = false
            }

            binding.root.setOnClickListener {
                selectedPos = adapterPosition
            }
        }
    }
}