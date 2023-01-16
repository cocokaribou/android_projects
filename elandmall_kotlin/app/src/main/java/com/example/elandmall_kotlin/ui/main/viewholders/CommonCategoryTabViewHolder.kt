package com.example.elandmall_kotlin.ui.main.viewholders

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewCommonCategoryTabBinding
import com.example.elandmall_kotlin.databinding.ViewCommonCategoryTabItemBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMdCateItemBinding
import com.example.elandmall_kotlin.model.Category
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

    inner class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CateItemHolder>(object :
        DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean = oldItem.title == newItem.title
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateItemHolder {
            return CateItemHolder(
                ViewCommonCategoryTabItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: CateItemHolder, position: Int) {
            holder.onBind()
        }

        inner class CateItemHolder(private val binding: ViewCommonCategoryTabItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                selectIndicator.isSelected = adapterPosition == cateSelected

                val data = currentList[adapterPosition]

                if (!data.image.isNullOrEmpty()) {
                    cateImg.visibility = View.VISIBLE
                    cateName.typeface = Typeface.DEFAULT
                    Glide.with(root.context)
                        .load("http:" + data.image)
                        .into(cateImg)
                    cateName.setLines(2)
                } else {
                    cateImg.visibility = View.GONE
                    cateName.typeface = Typeface.DEFAULT_BOLD
                    cateName.setLines(1)
                }

                cateName.text = data.title
                root.setOnClickListener {
                    if (adapterPosition != cateSelected) {
                        cateSelected = adapterPosition
                        data.payload2?.let {
                            cateSelector(listOf(data.payload1!!, data.payload2))

                            Toast.makeText(root.context, "${data.payload1}, ${data.payload2}", Toast.LENGTH_SHORT).show()
                        } ?: run {
                            cateSelector(listOf(data.payload1!!))

                            Toast.makeText(root.context, data.payload1, Toast.LENGTH_SHORT).show()
                        }
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}