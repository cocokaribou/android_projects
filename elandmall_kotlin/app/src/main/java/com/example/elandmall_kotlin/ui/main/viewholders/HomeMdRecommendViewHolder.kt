package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
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
import com.example.elandmall_kotlin.util.dpToPx

var selectedTab = 0

class HomeMdRecommendViewHolder(private val binding: ViewHomeMdBinding) : BaseViewHolder(binding.root) {
    lateinit var data: HomeResponse.HomeMd

    private val tabSelector: (Int) -> Unit = {
        selectedTab = it
        mCateAdapter.notifyDataSetChanged()

        mListAdapter.submitList(data.homeMdCatList?.get(it)?.homeMdGoods)
    }
    private val mCateAdapter by lazy { CategoryAdapter(tabSelector) }
    private val mListAdapter by lazy { MdListAdapter() }
    private val mItemDecoration by lazy { HorizontalSpacingItemDecoration(spacing = 1.dpToPx(), edgeSpacing = 0, includeEdge = false) }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeMdRecommendData)?.let {
            data = it.homeMdData
            initView(data)
        }
    }

    private fun initView(data: HomeResponse.HomeMd) = with(binding) {
        mCateAdapter.submitList(data.homeMdCatList)

        mdCateList.adapter = mCateAdapter
        if (mdCateList.itemDecorationCount == 0) {
            mdCateList.addItemDecoration(mItemDecoration)
        }

        mListAdapter.submitList(data.homeMdCatList?.get(selectedTab)?.homeMdGoods)
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