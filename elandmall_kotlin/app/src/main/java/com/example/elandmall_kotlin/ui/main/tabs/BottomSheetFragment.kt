package com.example.elandmall_kotlin.ui.main.tabs

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.FragmentBottomSheetBinding
import com.example.elandmall_kotlin.databinding.ViewDialogItemPlanDetailBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.main.viewholders.SortCallback
import com.example.elandmall_kotlin.util.Logger
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

var selected = 0

class BottomSheetFragment(
    private val sortCallback: SortCallback? = null,
    private val initIndex: Int,
    private val list: List<Any>,
) :
    BottomSheetDialogFragment() {
    private val mAdapter by lazy { DialogAdapter().apply { items = list } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentBottomSheetBinding.inflate(inflater).apply {
            dialogClose.setOnClickListener {
                dismiss()
            }
            dialogList.adapter = mAdapter
            dialogList.layoutManager = LinearLayoutManager(this@BottomSheetFragment.context, LinearLayoutManager.VERTICAL, false)
        }
        selected = initIndex

        return binding.root
    }

    inner class DialogAdapter : RecyclerView.Adapter<DialogAdapter.DialogItemHolder>() {
        var items: List<Any> = listOf()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogAdapter.DialogItemHolder {
            return DialogItemHolder(
                ViewDialogItemPlanDetailBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: DialogAdapter.DialogItemHolder, position: Int) {
            holder.onBind()
        }

        override fun getItemCount(): Int = items.size

        inner class DialogItemHolder(private val binding: ViewDialogItemPlanDetailBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                // list of String
                (items[adapterPosition] as? String)?.let { data ->
                    name.text = data

                    if (adapterPosition == selected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    } else {
                        selector.isSelected = false
                        name.setTextColor(Color.parseColor("#2b2b2b"))
                    }

                    root.setOnClickListener {
                        if (selected != adapterPosition) {
                            selected = adapterPosition
                            sortCallback?.invoke(adapterPosition)
                        }
                        dismiss()
                    }
                }

                // list of StorePick
                (items[adapterPosition] as? StoreShopResponse.StorePick)?.let { storeData ->
                    name.text = storeData.relContNm

                    if (adapterPosition == selected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    } else {
                        selector.isSelected = false
                        name.setTextColor(Color.parseColor("#2b2b2b"))
                    }

                    root.setOnClickListener {
                        if (selected != adapterPosition) {
                            selected = adapterPosition
                            sortCallback?.invoke(storeData)
                        }
                        dismiss()
                    }
                }
            }
        }
    }
}