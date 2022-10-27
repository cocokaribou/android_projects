package com.example.custom_logview

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.custom_logview.databinding.FragmentLogBinding
import com.example.custom_logview.databinding.ViewLogItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogBottomSheetFragment : BottomSheetDialogFragment() {
    private val mAdapter by lazy { LogAdapter() }

    lateinit var binding: FragmentLogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LogBottomSheetDialogTheme)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLogBinding.inflate(inflater).apply {
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        dialog?.window?.let {
            it.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        return binding.root
    }

    fun submitList(list: List<LogData>) {
        mAdapter.submitList(list)
    }
}

class LogAdapter : ListAdapter<LogData, LogAdapter.LogViewHolder>(diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<LogData>() {
            override fun areItemsTheSame(oldItem: LogData, newItem: LogData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: LogData, newItem: LogData): Boolean {
                return oldItem.time == newItem.time
            }

        }
    }

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

    class LogViewHolder(private val binding: ViewLogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(log: LogData) {
            binding.apply {
                root.setOnClickListener {
                    //NOP
                }

                if (log.isApiLog) {
                    msg.apply {
                        text = log.msg
                        setTextColor(log.color)
                    }
                    type.visibility = View.GONE
                } else {
                    msg.apply {
                        subStringColor(log.msg, log.msg.indexOf("\n"), log.msg.length)
                        setTextColor(Color.parseColor("#ffffff"))
                    }
                    type.apply {
                        visibility = View.VISIBLE
                        text = log.type.toString()
                        setBackgroundColor(log.color)
                    }
                }
            }
        }
        private fun TextView.subStringColor(text: String?, start: Int, end: Int) {
            val ssb = SpannableStringBuilder(text)

            ssb.setSpan(
                ForegroundColorSpan(Color.parseColor("#bbbbbb")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            this.text = ssb
        }
    }
}