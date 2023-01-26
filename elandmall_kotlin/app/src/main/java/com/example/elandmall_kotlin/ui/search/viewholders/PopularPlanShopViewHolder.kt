package com.example.elandmall_kotlin.ui.search.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.example.elandmall_kotlin.databinding.ViewSearchPopularPlanShopBinding
import com.example.elandmall_kotlin.databinding.ViewSearchPopularPlanShopItemBinding
import com.example.elandmall_kotlin.model.SearchPlanShopResponse
import com.example.elandmall_kotlin.ui.search.SearchBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx

class PopularPlanShopViewHolder(private val binding: ViewSearchPopularPlanShopBinding) : SearchBaseViewHolder(binding.root) {
    private val mAdapter by lazy { PlanShopAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>?)?.let {
            val dataList = it.filterIsInstance<SearchPlanShopResponse.Planshop>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<SearchPlanShopResponse.Planshop>) = with(binding) {
        mAdapter.submitList(data)

        list.adapter = mAdapter
        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(3, 10.dpToPx(), true))
        }
    }

    inner class PlanShopAdapter : ListAdapter<SearchPlanShopResponse.Planshop, PlanShopAdapter.PlanShopHolder>(object :
        DiffUtil.ItemCallback<SearchPlanShopResponse.Planshop>() {
        override fun areItemsTheSame(oldItem: SearchPlanShopResponse.Planshop, newItem: SearchPlanShopResponse.Planshop): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SearchPlanShopResponse.Planshop, newItem: SearchPlanShopResponse.Planshop): Boolean =
            oldItem.dispCtgNo == newItem.dispCtgNo
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PlanShopHolder(
                ViewSearchPopularPlanShopItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: PlanShopHolder, position: Int) {
            holder.onBind()
        }

        inner class PlanShopHolder(private val binding: ViewSearchPopularPlanShopItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]

                Glide.with(root.context)
                    .load(item.bannerImgUrl)
                    .into(img)

                title.text = item.dispCtgNm
            }
        }
    }
}