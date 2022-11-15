package com.example.elandmall_kotlin.ui.main.viewholders

import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeMainBannerBinding
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.getSpannedBoldText

class HomeMainBannerViewHolder(private val binding: ViewHomeMainBannerBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {

        (item as? ModuleData.HomeMainBannerData?)?.let {
            initView(it.homeBannerData)
        }
    }

    private fun initView(data: List<HomeResponse.HomeMainbanner>) = with(binding) {
        data.forEach { data ->
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

            data.linkUrl?.let { link ->
                root.setOnClickListener {
                    EventBus.fire(LinkEvent(link))
                }
            }
        }

        flipper.apply {
            isNestedScrollingEnabled = true
            startFlipping()
        }

        popup.setOnClickListener {
            EventBus.fire(LinkEvent(data[flipper.displayedChild].linkUrl))
        }

        counter.apply {
            val count = "${flipper.displayedChild + 1}/${data.size}"
            text = getSpannedBoldText(count, count.split("/")[0])
        }
    }

}