package com.example.elandmall_kotlin.ui.search.viewholders

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewSearchPopularRankingBinding
import com.example.elandmall_kotlin.databinding.ViewSearchPopularRankingItemBinding
import com.example.elandmall_kotlin.model.SearchPopularResponse
import com.example.elandmall_kotlin.ui.search.SearchBaseViewHolder
import java.lang.NumberFormatException

class PopularRankingViewHolder(private val binding: ViewSearchPopularRankingBinding) : SearchBaseViewHolder(binding.root) {
    private val mAdapter by lazy { PopularAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<SearchPopularResponse.PopularItem>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<SearchPopularResponse.PopularItem>) = with(binding) {
        mAdapter.submitList(data)

        list.adapter = mAdapter
    }

    inner class PopularAdapter :
        ListAdapter<SearchPopularResponse.PopularItem, PopularAdapter.PopularHolder>(object :
            DiffUtil.ItemCallback<SearchPopularResponse.PopularItem>() {
            override fun areItemsTheSame(oldItem: SearchPopularResponse.PopularItem, newItem: SearchPopularResponse.PopularItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SearchPopularResponse.PopularItem,
                newItem: SearchPopularResponse.PopularItem
            ) = oldItem.brandNm == newItem.brandNm
        }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PopularHolder(
                ViewSearchPopularRankingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: PopularHolder, position: Int) {
            holder.onBind()
        }

        inner class PopularHolder(private val binding: ViewSearchPopularRankingItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]
                val rank = adapterPosition + 1

                ranking.text = rank.toString()
                ranking.setTextColor(Color.parseColor(if (rank <= 5) "#c9000b" else "#444444"))
                title.text = item.brandNm

                try {
                    val rankDiff = item.rankDiff.toInt()
                    diff.apply {
                        scaleX = 0.7f
                        scaleY = 0.7f
                        setImageResource(if (rankDiff > 0) R.drawable.ic_arrow_up else if (rankDiff == 0) R.drawable.ic_bar else R.drawable.ic_arrow_down)
                        imageTintList = ColorStateList.valueOf(Color.parseColor(if (rankDiff > 0) "#c9000b" else "#cdcdcd"))
                    }
                } catch (e: NumberFormatException) {
                    diff.setImageResource(R.drawable.ic_new)
                }
            }
        }
    }

}