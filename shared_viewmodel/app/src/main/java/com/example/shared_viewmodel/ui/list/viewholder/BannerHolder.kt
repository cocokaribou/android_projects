package com.example.shared_viewmodel.ui.list.viewholder

import android.annotation.SuppressLint
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.shared_viewmodel.LogHelper
import com.example.shared_viewmodel.databinding.ItemBannerBinding
import com.example.shared_viewmodel.databinding.VhMainHomeBannerBinding
import com.example.shared_viewmodel.model.HomeMainBanner
import com.example.shared_viewmodel.model.ModuleData
import com.example.shared_viewmodel.ui.list.BaseHolder
import com.example.shared_viewmodel.ui.list.Click
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class BannerHolder(private val itemBinding: VhMainHomeBannerBinding, lifecycleOwner: LifecycleOwner) : BaseHolder(itemBinding.root) {
    private var totalPage = 0

    var isScrollStopped = false
    var isSettled = false

    private val job = lifecycleOwner.lifecycleScope.launch {
        while (lifecycleOwner.lifecycleScope.isActive) {
            delay(2000L)
            val nextPage =
                if (itemBinding.viewpager.currentItem == totalPage-1) {
                    0
                } else {
                    itemBinding.viewpager.currentItem + 1
                }
            if (!isSettled && !isScrollStopped) {
                itemBinding.viewpager.currentItem = nextPage
            }
        }
    }

    init {
        job.start()
        lifecycleOwner.lifecycle.addObserver(object: LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when(event) {
                    Lifecycle.Event.ON_RESUME -> {
                        LogHelper.v("⚠️ frag resumed")
                        if (isScrollStopped) {
                            startAutoScroll()
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        LogHelper.v("⚠️ frag paused")
                        stopAutoScroll()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        LogHelper.v("⚠️ frag destroyed")
                        job.cancel()
                        LogHelper.v("⚠️ is coroutine active ${job.isActive}")
                    }
                }
            }

        })
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        var updatePosition: ((Int) -> Unit)? = {}

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updatePosition?.invoke(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when (state) {
                ViewPager2.SCROLL_STATE_IDLE -> {
                    if (!isSettled && !isScrollStopped) {
                        startAutoScroll()
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun bind(item: ModuleData, listener: Click) {
        LogHelper.v("⚠️onBind")
        totalPage = item.bannerList?.size ?: 0

        itemBinding.viewpager.apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            registerOnPageChangeCallback(pageChangeCallback)
            item.bannerList?.let {
                adapter = BannerPagerAdapter(it)
            }
            getChildAt(0).setOnTouchListener { _, event ->
                when(event.action) {
                    // 눌렀을 때
                    MotionEvent.ACTION_MOVE -> {
                        isSettled = true
                    }
                    // 뗐을 때
                    MotionEvent.ACTION_UP -> {
                        isSettled = false
                    }
                }
                false
            }
        }
        itemBinding.tvIndicator.apply {
            text = "1/$totalPage"
        }

        itemBinding.toggle.apply {
            setOnClickListener{
                if (isScrollStopped) {
                    text = "||"
                    startAutoScroll()
                } else {
                    text = "▶"
                    stopAutoScroll()
                }
            }
        }
        pageChangeCallback.updatePosition = { pos ->
            itemBinding.tvIndicator.text = "${pos + 1}/$totalPage"
        }
    }

    private fun startAutoScroll() {
        isScrollStopped = false
        LogHelper.v("시작")
    }

    private fun stopAutoScroll() {
        isScrollStopped = true
        LogHelper.v("스돕 isActive ${job.isActive}")
    }

    override fun onAppeared() {
        super.onAppeared()
        startAutoScroll()
    }

    override fun onDisappeared() {
        super.onDisappeared()
        if (!isScrollStopped) {
            stopAutoScroll()
        }
    }


    class BannerPagerAdapter(private val bannerDataList: List<HomeMainBanner>) : RecyclerView.Adapter<BannerPagerAdapter.BannerPagerHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerPagerHolder {
            val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return BannerPagerHolder(binding)
        }

        override fun onBindViewHolder(holder: BannerPagerHolder, position: Int) {
            holder.onBind(bannerDataList[position])
        }

        override fun getItemCount(): Int = bannerDataList.size

        inner class BannerPagerHolder(private val itemBinding: ItemBannerBinding) : RecyclerView.ViewHolder(itemBinding.root) {
            fun onBind(data: HomeMainBanner) {
                with(itemBinding) {
                    Glide.with(root.context).load(data.imgPath).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                LogHelper.v("image load fail")
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }
                        }).into(iv)

                    if (data.txtDispYn == "Y") {
                        tvBrand.text = data.brandNm
                        tvTitle.text = data.txt
                        tvSubtitle.text = data.subTxt
                    } else {
                        tvBrand.visibility = View.GONE
                        tvTitle.visibility = View.GONE
                        tvSubtitle.visibility = View.GONE
                        viewDim.visibility = View.GONE
                    }
                }
            }
        }
    }
}