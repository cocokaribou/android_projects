package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanBinding
import com.example.elandmall_kotlin.databinding.ViewHomeSeasonPlanItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData

class HomeSeasonPlanViewHolder(private val binding: ViewHomeSeasonPlanBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeSeasonPlanData)?.let {
            initView(it.homeSeasonPlansData)
        }
    }

    private fun initView(data: HomeResponse.HomeSeasonPlan.HomeSeasonPlanItem) = with(binding){
        Glide.with(itemView.context)
            .load(data.imageUrl)
            .into(seasonImage)

        seasonList.apply {
            adapter = SeasonPlanAdapter(data.goodsList ?: listOf())
        }
    }
}

class SeasonPlanAdapter(val list: List<Goods>): RecyclerView.Adapter<SeasonPlanAdapter.SeasonPlanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonPlanViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SeasonPlanViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = list.size

    inner class SeasonPlanViewHolder(val binding: ViewHomeSeasonPlanItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            TODO("draw UI")
        }
    }
}