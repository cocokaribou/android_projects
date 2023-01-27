package com.example.elandmall_kotlin.ui.search.viewholders

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.ui.search.SearchBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class BrandTop10ViewHolder(private val binding: ViewSearchBrandTop10Binding) : SearchBaseViewHolder(binding.root) {
    private val mAdapter by lazy { Top10Adapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<String>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<String>) = with(binding) {
        mAdapter.submitList(data)

        list.adapter = mAdapter
        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(HorizontalSpacingItemDecoration(8.dpToPx(), 10.dpToPx(), true))
        }
    }

    inner class Top10Adapter : ListAdapter<String, Top10Adapter.Top10Holder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Top10Holder(
                ViewSearchBrandTop10ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: Top10Holder, position: Int) {
            holder.onBind()
        }

        inner class Top10Holder(private val binding: ViewSearchBrandTop10ItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]

                val format = root.context.getString(R.string.search_brand_top10_format)
                val string = String.format(format, adapterPosition + 1, item)
                title.text = Html.fromHtml(string)
            }
        }
    }
}