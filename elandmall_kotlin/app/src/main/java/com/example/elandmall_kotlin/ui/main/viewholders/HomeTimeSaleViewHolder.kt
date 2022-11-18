package com.example.elandmall_kotlin.ui.main.viewholders

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeTimeSaleBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.model.HomeResponse
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.getSpannedSizeText
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class HomeTimeSaleViewHolder(private val binding: ViewHomeTimeSaleBinding, val lifecycleOwner: LifecycleOwner) :
    BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeTimeData)?.homeTimeData?.let {
            initView(it)
        }
    }

    private fun initView(data: Goods) = with(binding) {
        // common ui
        drawGoodsUI(binding, data)

        // timer
        data.time?.let {
            tickerFlow(interval = 1.seconds, end = it)
                .onEach { duration ->
                    if (duration < 0L) {
                        binding.timer.text = "Finshed."
                        awaitCancellation()
                    } else {
                        binding.timer.text = duration.toTimeString()
                    }
                }
                .launchIn(lifecycleOwner.lifecycleScope)
        }
    }

    private fun tickerFlow(interval: Duration, end: String) = flow {
        delay(0L)
        while (true) {
            emit(getDurationSec(end))
            delay(interval)
        }
    }

    private fun getDurationSec(end: String): Long {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
            val dDay = LocalDateTime.parse(end, formatter)
            val currDay = LocalDateTime.now()

            return currDay.until(dDay, ChronoUnit.SECONDS)
        } else {
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val dDay = formatter.parse(end)
            val currDay = Date()

            return dDay.time - currDay.time
        }
    }

    private fun Long.toTimeString(): String {
        val hours = this / 3600
        val minutes = (this % 3600) / 60
        val seconds = this % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}