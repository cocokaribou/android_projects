package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.elandmall_kotlin.databinding.ViewEkidsCategoryBinding
import com.example.elandmall_kotlin.databinding.ViewEkidsCategoryItemBinding
import com.example.elandmall_kotlin.model.EKidsResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData

class EKidsCategoryViewHolder(private val binding: ViewEkidsCategoryBinding) : BaseViewHolder(binding.root) {
    private val mAdapter by lazy { IconAdapter() }
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.EKidsCategoryData)?.let {
            initView(it.eKidsCateList)
        }
    }

    private fun initView(data: List<EKidsResponse.Ctg>) {
        mAdapter.submitList(data)
        binding.list.adapter = mAdapter
    }

    inner class IconAdapter : ListAdapter<EKidsResponse.Ctg, IconAdapter.IconHolder>(object : DiffUtil.ItemCallback<EKidsResponse.Ctg>() {
        override fun areItemsTheSame(oldItem: EKidsResponse.Ctg, newItem: EKidsResponse.Ctg): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: EKidsResponse.Ctg, newItem: EKidsResponse.Ctg): Boolean = oldItem.ctgNm == newItem.ctgNm
    }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder {
            return IconHolder(
                ViewEkidsCategoryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: IconHolder, position: Int) {
            holder.onBind()
        }

        inner class IconHolder(private val binding: ViewEkidsCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val currentItem = currentList[adapterPosition]

                Glide.with(root.context)
                    .load("http:" + currentItem.imgPath)
                    .override(ImageViewTarget.SIZE_ORIGINAL, ImageViewTarget.SIZE_ORIGINAL)
                    .into(icon)

                iconTitle.text = currentItem.ctgNm

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(currentItem.linkUrl))
                }
            }
        }
    }
}