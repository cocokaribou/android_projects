package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewHomeBrandBinding
import com.example.elandmall_kotlin.databinding.ViewHomeBrandItemBinding
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.HorizontalSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class HomeBrandViewHolder(private val binding: ViewHomeBrandBinding) : BaseViewHolder(binding.root) {

    private lateinit var mDecoration : RecyclerView.ItemDecoration
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeBrandData)?.let {
            initView(it.homeBrandData)
        }
    }

    private fun initView(data: List<HomeResponse.HomeBrand>) {
        binding.brandList.adapter = BrandIconAdapter(data)

        if (!this::mDecoration.isInitialized) {
            mDecoration = HorizontalSpacingItemDecoration(14.dpToPx(), 15.dpToPx(), true)
            binding.brandList.addItemDecoration(mDecoration)
        }
    }
}

class BrandIconAdapter(val list: List<HomeResponse.HomeBrand>) : RecyclerView.Adapter<BrandIconAdapter.BrandIconViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandIconViewHolder {
        return BrandIconViewHolder(
            ViewHomeBrandItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BrandIconViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount() = list.size

    inner class BrandIconViewHolder(val binding: ViewHomeBrandItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeResponse.HomeBrand) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(binding.brandImg)

            binding.brandName.text = data.brandName

            binding.root.setOnClickListener {
                EventBus.fire(LinkEvent(data.linkUrl))
            }
        }
    }
}