package com.example.custom_logview.ui

import android.app.Dialog
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.custom_logview.databinding.DialogLogDetailBinding
import com.example.custom_logview.databinding.ViewLogItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LogAdapter : ListAdapter<LogData, LogAdapter.LogViewHolder>(object : DiffUtil.ItemCallback<LogData>() {
    override fun areItemsTheSame(oldItem: LogData, newItem: LogData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LogData, newItem: LogData): Boolean {
        return oldItem.time == newItem.time
    }

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        return LogViewHolder(
            ViewLogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class LogViewHolder(val binding: ViewLogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val listener: () -> Unit = {
            binding.root.setBackgroundColor(Color.TRANSPARENT)
        }

        fun bind(log: LogData) {
            binding.apply {
                // 클릭시 api 상세
                root.setOnClickListener {
                    if (log.isSuccessful) {
                        root.setBackgroundColor(Color.parseColor("#000000"))
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(200)
                            logDetailDialog(log, listener)
                        }
                    }
                }

                // 로그텍스트
                msg.apply {
                    if (log.isApiLog) {
                        text = log.msg
                        setTextColor(log.color)
                    } else {
                        subStringColor(log.msg, log.msg.indexOf("\n"), log.msg.length, Color.parseColor("#bbbbbb"))
                        setTextColor(Color.parseColor("#ffffff"))
                    }
                }

                // 로그타입
                type.apply {
                    if (log.isApiLog) {
                        visibility = View.GONE
                    } else {
                        visibility = View.VISIBLE
                        text = log.type.toString()
                        setBackgroundColor(log.color)
                    }
                }

            }
        }

        private fun logDetailDialog(log: LogData, listener: () -> Unit) {
            Dialog(binding.root.context).apply {
                val view = DialogLogDetailBinding.inflate(LayoutInflater.from(binding.root.context)).apply {
                    val message ="${log.type}  ${log.body}"
                    msg.subStringColor(message, 0, log.type.toString().length, log.color)
                }

                setContentView(view.root)

                window?.apply {
                    setBackgroundDrawableResource(android.R.color.transparent)
                    setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
                    clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                }
                setOnDismissListener {
                    listener.invoke()
                }
            }.show()
        }
    }
}

private fun TextView.subStringColor(text: String?, start: Int, end: Int, color: Int) {
    val ssb = SpannableStringBuilder(text)

    ssb.setSpan(
        ForegroundColorSpan(color),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    this.text = ssb
}
