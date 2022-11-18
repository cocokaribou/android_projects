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

var selectedTab = 0

class HomeMdViewHolder(private val binding: ViewHomeMdBinding) : BaseViewHolder(binding.root) {
    lateinit var data: HomeResponse.HomeMd

    private val tabSelector: (Int) -> Unit = {
        // position before
        mCateAdapter.notifyItemChanged(selectedTab)

        // position after
        Logger.v("clicked! $it")
        selectedTab = it
        mCateAdapter.notifyItemChanged(selectedTab)
        mListAdapter.submitList(data.homeMdCatList?.get(selectedTab)?.homeMdGoods)
    }
    private val mCateAdapter by lazy { CategoryAdapter(tabSelector) }
    private val mListAdapter by lazy { MdListAdapter() }
    lateinit var mItemDecoration: HorizontalSpacingItemDecoration

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeMdData)?.let {
            data = it.homeMdData
            initView(data)
        }
    }

    private fun initView(data: HomeResponse.HomeMd) = with(binding) {
        mCateAdapter.submitList(data.homeMdCatList)
        if (!::mItemDecoration.isInitialized) {
            mItemDecoration = HorizontalSpacingItemDecoration(spacing = 3, edgeSpacing = 1, includeEdge = false)
            mdCateList.addItemDecoration(mItemDecoration)
        }
        mdCateList.adapter = mCateAdapter


        mListAdapter.submitList(data.homeMdCatList?.get(0)?.homeMdGoods)
        mdGoodsList.adapter = mListAdapter
    }
}

class CategoryAdapter(private val tabSelector: (Int) -> Unit) : ListAdapter<HomeResponse.HomeMd.HomeMdCat, CategoryAdapter.MdCateViewHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<HomeResponse.HomeMd.HomeMdCat>() {
            override fun areItemsTheSame(oldItem: HomeResponse.HomeMd.HomeMdCat, newItem: HomeResponse.HomeMd.HomeMdCat): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HomeResponse.HomeMd.HomeMdCat, newItem: HomeResponse.HomeMd.HomeMdCat): Boolean {
                return oldItem.menuTitle == newItem.menuTitle
            }

        }
    }


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
        holder.onBind(currentList[position], tabSelector)
    }

    inner class MdCateViewHolder(private val binding: ViewHomeMdCateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeResponse.HomeMd.HomeMdCat, tabSelector: (Int) -> Unit) = with(binding) {
            Glide.with(itemView.context)
                .load("http:" + data.imageUrl)
                .into(cateImg)

            if (adapterPosition == selectedTab) {
                bar.visibility = View.VISIBLE
            } else {
                bar.visibility = View.GONE
            }

            cateName.text = data.menuTitle
            root.setOnClickListener {
                tabSelector(adapterPosition)
            }
        }
    }
}

class MdListAdapter : ListAdapter<Goods, MdListAdapter.MdViewHolder>(goodsDiff) {
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
        holder.onBind(currentList[position])
    }

    inner class MdViewHolder(private val binding: ViewHomeMdItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods) {
            drawGoodsUI(binding, data)
        }
    }
}