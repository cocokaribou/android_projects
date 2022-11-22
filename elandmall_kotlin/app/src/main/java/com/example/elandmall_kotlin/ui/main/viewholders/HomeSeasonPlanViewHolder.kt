package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanBinding
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.goodsDiff
import com.example.elandmall_kotlin.util.getSpannedSizeText

class HomeSeasonPlanViewHolder(private val binding: ViewHomeSeasonPlanBinding) : BaseViewHolder(binding.root) {
    val mAdapter by lazy { SeasonPlanAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeSeasonPlanData)?.let {
            initView(it.homeSeasonPlansData)
        }
    }

    private fun initView(data: HomeResponse.HomeSeasonPlan.HomeSeasonPlanItem) = with(binding) {
        Glide.with(itemView.context)
            .load("http:" + data.imageUrl)
            .into(seasonImage)

        seasonList.adapter = mAdapter
        mAdapter.submitList(data.goodsList ?: listOf())
    }

    inner class SeasonPlanAdapter : ListAdapter<Goods, SeasonPlanAdapter.SeasonPlanViewHolder>(goodsDiff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonPlanViewHolder {
            return SeasonPlanViewHolder(
                ViewHomeSeasonPlanItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: SeasonPlanViewHolder, position: Int) {
            holder.onBind()
        }

        inner class SeasonPlanViewHolder(val binding: ViewHomeSeasonPlanItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() {
                drawGoodsUI(binding, currentList[adapterPosition])
            }
        }
    }
}