package com.example.elandmall_kotlin.ui.main.viewholders

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeMainBannerBinding
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.Logger

class HomeMainBannerViewHolder(private val binding: ViewHomeMainBannerBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {

        (item as? ModuleData.HomeMainBannerData?)?.let {
            initView(it)
        }

    }

    private fun initView(data: ModuleData.HomeMainBannerData) = with(binding) {
        data.homeBannerData.forEachIndexed { _, data ->
            flipper.addView(ImageView(itemView.context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                )
                Glide.with(itemView.context)
                    .load(data.imageUrl)
                    .error(R.drawable.noimg_1080x611)
                    .placeholder(R.drawable.noimg_1080x611)
                    .into(this)
            })
        }
    }

}