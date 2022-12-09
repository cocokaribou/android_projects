package com.example.elandmall_kotlin.ui.main.viewholders

import android.os.Build
import android.widget.TextView
import androidx.core.view.forEach
import androidx.lifecycle.*
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewHomeTimeSaleBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.util.GoodsUtil.drawGoodsUI
import kotlinx.coroutines.Job
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

class HomeTimeSaleViewHolder(private val binding: ViewHomeTimeSaleBinding, private val lifecycleOwner: LifecycleOwner) :
    BaseViewHolder(binding.root) {

    private var endTime: String = "2000/01/01 00:00:00"
    var job: Job? = null

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        stopTimer()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        if (itemView.isAttachedToWindow) {
                            startTimer()
                        }
                    }
                }
            }
        })
    }

    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.HomeTimeSaleData)?.homeTimeSaleData?.let {
            initView(it)
        }
    }

    private fun initView(data: Goods) = with(binding) {
        // common ui
        drawGoodsUI(binding, data)
        binding.tag.forEach {
            (it as TextView).setTextAppearance(R.style.home_tag_style)
        }

        // timer
        data.time?.let {
            endTime = it
        }
    }

    override fun onAppeared() {
        startTimer()
    }

    override fun onDisappeard() {
        stopTimer()
    }

    private fun startTimer() {
        job = tickerFlow(interval = 1.seconds, end = endTime)
            .onEach { diff ->
                if (diff < 0L) {
                    binding.timer.text = "Finshed."
                    awaitCancellation()
                } else {
                    binding.timer.text = diff.toTimeString()
                }
            }
            .launchIn(lifecycleOwner.lifecycleScope)
    }

    private fun stopTimer() {
        job?.cancel()
    }

    private fun tickerFlow(interval: Duration, end: String) = flow {
        delay(0L)
        while (true) {
            emit(getRemainingTime(end))
            delay(interval)
        }
    }

    private fun getRemainingTime(end: String): Long {
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