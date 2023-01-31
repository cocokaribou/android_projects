package com.example.elandmall_kotlin.ui.main.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.example.elandmall_kotlin.databinding.ViewCommonMainBannerBinding
import com.example.elandmall_kotlin.databinding.ViewCommonMainBannerItemBinding
import com.example.elandmall_kotlin.model.Banner
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

var viewPagerIndex = 0

class CommonMainBannerViewHolder(
    private val binding: ViewCommonMainBannerBinding, private val lifecycleOwner: LifecycleOwner
) : BaseViewHolder(binding.root) {

    private val mAdapter by lazy { MainBannerPagerAdapter() }

    var bannerList = listOf<Banner>()
    var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        stopAutoScroll()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        if (itemView.isAttachedToWindow) {
                            startAutoScroll()
                        }
                    }
                }
            }
        })
    }

    override fun onAppeared() {
        binding.viewpager.currentItem = viewPagerIndex
        startAutoScroll()
    }

    override fun onDisappeard() {
        stopAutoScroll()
    }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.CommonMainBannerData?)?.let {
            bannerList = it.mainBannerData
            initView(it)
        }
    }

    private fun initView(data: ModuleData.CommonMainBannerData) = with(binding) {
        viewpager.apply {
            mAdapter.submitList(bannerList)
            adapter = mAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val count = "${position + 1}/${bannerList.size}"
                    counter.text = getSpannedBoldText(count, count[0].toString())
                }
            })
        }

        counter.text = getSpannedBoldText("1/${bannerList.size}", "1")

        indicator.setOnClickListener {
            EventBus.fire(LinkEvent(bannerList[viewpager.currentItem].linkUrl))
        }
        if (data.isDividerVisible) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.GONE
        }
        // TODO indicator style
        if (data.isIndicatorVisible) {
            indicator.visibility = View.VISIBLE
        } else {
            indicator.visibility = View.GONE
        }
    }

    private fun startAutoScroll() {
        job = tickerFlow(interval = 2.seconds)
            .onEach {
                binding.viewpager.currentItem = viewPagerIndex
                if (viewPagerIndex >= bannerList.size - 1) {
                    viewPagerIndex = 0
                } else {
                    viewPagerIndex++
                }
            }
            .launchIn(lifecycleOwner.lifecycleScope)
    }

    private fun stopAutoScroll() {
        job?.cancel()
    }

    private fun tickerFlow(interval: Duration) = flow {
        while (true) {
            emit(0)
            delay(interval)
        }
    }

    inner class MainBannerPagerAdapter : ListAdapter<Banner, MainBannerPagerAdapter.MainBannerItemViewHolder>(object :
        DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean =
            oldItem.linkUrl == newItem.linkUrl
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBannerItemViewHolder {
            return MainBannerItemViewHolder(
                ViewCommonMainBannerItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: MainBannerItemViewHolder, position: Int) {
            holder.onBind()
        }

        inner class MainBannerItemViewHolder(private val binding: ViewCommonMainBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = currentList[adapterPosition]

                Glide.with(root.context)
                    .load(data.imageUrl)
                    .override(getScreenWidthToPx(), ImageViewTarget.SIZE_ORIGINAL)
                    .into(AdjustHeightImageViewTarget(bannerImg))

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(data.linkUrl))
                }
            }
        }
    }
}