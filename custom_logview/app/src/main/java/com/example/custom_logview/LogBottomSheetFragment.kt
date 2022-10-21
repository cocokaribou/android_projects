package com.example.custom_logview

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias LogListener = (Int) -> Unit

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
        return binding.root
    }

    fun submitList(list: List<LogData>) {
        mAdapter.submitList(list)
    }
}

class LogAdapter : ListAdapter<LogData, LogAdapter.LogViewHolder>(diff) {

    var listener: (Int) -> Unit = {}
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
        listener = { LogDialog.Builder()
            .setBodyText(currentList[position].body)
            .create()
            .show((holder.itemView.context as BaseActivity).supportFragmentManager, "")
        }
        holder.bind(currentList[position], listener)
    }

    class LogViewHolder(val binding: ViewLogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(log: LogData, listener: LogListener) {
            binding.apply {
                root.setOnClickListener {
                    msg.alpha = 0.5f
                }
                msg.text = log.msg

                if (log.isApiLog) {
                    msg.setTextColor(log.color)
                    type.visibility = View.GONE
                } else {
                    msg.setTextColor(Color.parseColor("#ffffff"))
                    type.apply {
                        visibility = View.VISIBLE
                        text = log.type.toString()
                        setBackgroundColor(log.color)
                    }
                }
            }
        }
    }
}