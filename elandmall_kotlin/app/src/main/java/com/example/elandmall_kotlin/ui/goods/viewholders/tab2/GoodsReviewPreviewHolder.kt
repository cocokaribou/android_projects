package com.example.elandmall_kotlin.ui.goods.viewholders.tab2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewPreviewBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewPreviewItemBinding
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.GridSideSpacingItemDecoration
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx

class GoodsReviewPreviewHolder(val binding: ViewGoodsReviewPreviewBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { PreviewAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<String>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<String>) = with(binding.root) {
        adapter = mAdapter

        if (data.size >= 5) data.subList(0, 4)
        mAdapter.submitList(data)

        if (itemDecorationCount == 0) {
            addItemDecoration(GridSideSpacingItemDecoration(5, 10.dpToPx()))
        }
    }

    class PreviewAdapter : ListAdapter<String, PreviewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem.equals(newItem)
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PreviewHolder(
                ViewGoodsReviewPreviewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: PreviewHolder, position: Int) {
            val isLast = position == 4
            holder.onBind(currentList[position], isLast)
        }
    }

    class PreviewHolder(val binding: ViewGoodsReviewPreviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String, isLast: Boolean) = with(binding) {
            Glide.with(root.context)
                .load("http:$data")
                .into(goodsImg)

            more.visibility = if (isLast) View.VISIBLE else View.GONE
        }
    }
}