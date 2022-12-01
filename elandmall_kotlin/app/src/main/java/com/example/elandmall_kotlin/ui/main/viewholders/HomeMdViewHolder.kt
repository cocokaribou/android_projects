package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeMdBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMdCateItemBinding
import com.example.elandmall_kotlin.databinding.ViewHomeMdItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.goodsDiff
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx
import com.example.elandmall_kotlin.util.pxToDp

var selectedTab = 0

class HomeMdViewHolder(private val binding: ViewHomeMdBinding) : BaseViewHolder(binding.root) {
    lateinit var data: HomeResponse.HomeMd

    private val tabSelector: (Int) -> Unit = {
        selectedTab = it
        mListAdapter.submitList(data.homeMdCatList?.get(it)?.homeMdGoods)
    }
    private val mCateAdapter by lazy { CategoryAdapter(tabSelector) }
    private val mListAdapter by lazy { MdListAdapter() }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeMdData)?.let {
            data = it.homeMdData
            initView(data)
        }
    }

    private fun initView(data: HomeResponse.HomeMd) = with(binding) {
        mCateAdapter.submitList(data.homeMdCatList)
        mdCateList.adapter = mCateAdapter


        mListAdapter.submitList(data.homeMdCatList?.get(0)?.homeMdGoods)
        mdGoodsList.adapter = mListAdapter
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
                selectIndicator.isSelected = adapterPosition == 0

                val data = currentList[adapterPosition]
                Glide.with(itemView.context)
                    .load("http:" + data.imageUrl)
                    .into(cateImg)

                if (adapterPosition == currentList.size - 1) {
                    spacing.visibility = View.GONE
                } else {
                    spacing.visibility = View.VISIBLE
                }

                cateName.text = data.menuTitle
                root.setOnClickListener {
                    selectIndicator.isSelected = !selectIndicator.isSelected
                    tabSelector(adapterPosition)
                }
            }
        }
    }

    inner class MdListAdapter : ListAdapter<Goods, MdListAdapter.MdViewHolder>(goodsDiff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MdViewHolder {
            return MdViewHolder(
                ViewHomeMdItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MdViewHolder, position: Int) {
            holder.onBind()
        }

        inner class MdViewHolder(private val binding: ViewHomeMdItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() {
                drawGoodsUI(binding, currentList[adapterPosition])
            }
        }
    }
}