package com.example.elandmall_kotlin.ui.search.viewholders

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.model.ModuleBrandData
import com.example.elandmall_kotlin.model.SearchBrandKeyword
import com.example.elandmall_kotlin.ui.search.SearchBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx

var isKorean = true
var savedIndex = 0
var clicker: (Boolean, Int) -> Unit = { _, _ -> }

class BrandAlphabetsViewHolder(private val binding: ViewSearchBrandAlphabetsBinding) : SearchBaseViewHolder(binding.root) {
    private val mAdapter by lazy { AlphabetAdapter() }
    override fun onBind(item: Any?) {
        (item as? ModuleBrandData)?.let {
            initUI(it)
        }
    }

    private fun initUI(moduleData: ModuleBrandData) = with(binding) {
        isKorean = moduleData.isKorean
        clicker = moduleData.click!!

        mAdapter.submitList(moduleData.data)

        list.adapter = mAdapter
        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(6, 10.dpToPx(), true))
        }

    }

    inner class AlphabetAdapter : ListAdapter<String, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }) {

        override fun getItemViewType(position: Int): Int = currentList.indices.elementAt(position)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (viewType != currentList.size - 1) {
                AlphabetHolder(
                    ViewSearchBrandAlphabetsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            } else
                RefreshHolder(
                    ViewSearchBrandAlphabetsRefreshBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }

        inner class AlphabetHolder(private val binding: ViewSearchBrandAlphabetsItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val brandNm = currentList[adapterPosition]

                if (brandNm.isEmpty()) {
                    root.visibility = View.GONE
                    return
                }

                title.text = brandNm

                if (savedIndex == adapterPosition) {
                    bg.strokeColor = Color.parseColor("#c9000b")
                    title.typeface = Typeface.DEFAULT_BOLD
                    title.setTextColor(Color.parseColor("#c9000b"))
                } else {
                    bg.strokeColor = Color.parseColor("#efefef")
                    title.typeface = Typeface.DEFAULT
                    title.setTextColor(Color.parseColor("#2b2b2b"))
                }

                root.setOnClickListener {
                    savedIndex = adapterPosition
                    clicker(isKorean, savedIndex)
                    notifyDataSetChanged()
                }
            }
        }

        inner class RefreshHolder(private val binding: ViewSearchBrandAlphabetsRefreshBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                lang.text = if (isKorean) "ㄱ" else "A"

                root.setOnClickListener {
                    isKorean = !isKorean
                    clicker(isKorean, 0)
                    notifyDataSetChanged()
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as? AlphabetHolder)?.onBind()
            (holder as? RefreshHolder)?.onBind()
        }
    }
}