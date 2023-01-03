package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanBinding
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.model.Plan
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.GoodsUtil.goodsDiff
import com.example.elandmall_kotlin.util.getScreenWidthToPx

class CommonPlanViewHolder(private val binding: ViewHomeSeasonPlanBinding) : BaseViewHolder(binding.root) {
    val mAdapter by lazy { SeasonPlanAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonPlanData)?.let {
            initView(it.planData)
        }
    }

    private fun initView(data: Plan) = with(binding) {
        Glide.with(root.context)
            .load("http:" + data.imageUrl)
            .override(getScreenWidthToPx(), getScreenWidthToPx())
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
                // weight
                binding.root.layoutParams.width = getScreenWidthToPx() / 3

                drawGoodsUI(binding, currentList[adapterPosition])
            }
        }
    }
}