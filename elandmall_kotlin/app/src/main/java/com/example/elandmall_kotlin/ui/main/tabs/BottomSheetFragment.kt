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
import com.example.elandmall_kotlin.databinding.ViewDialogItemStorePickBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.util.Logger
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class DialogType {
    STORE_PICK,
    COMMON_SORT,
    OTHER
}

var storeSelected = 0
var sortSelected = 1

class BottomSheetFragment(
    private val type: DialogType,
    private val list: List<Any>
) :
    BottomSheetDialogFragment() {
    private val mAdapter by lazy { DialogAdapter().apply { items = list } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBottomSheetBinding.inflate(inflater).apply {
            dialogClose.setOnClickListener {
                dismiss()
            }
            dialogList.adapter = mAdapter

            when (type) {
                DialogType.STORE_PICK -> {
                    dialogTitle.text = "스토어픽 지점"
                    dialogList.layoutManager = LinearLayoutManager(this@BottomSheetFragment.context, LinearLayoutManager.VERTICAL, false)
                }
                DialogType.COMMON_SORT -> {
                    dialogTitle.text = "상품분류"
                    dialogList.layoutManager = LinearLayoutManager(this@BottomSheetFragment.context, LinearLayoutManager.VERTICAL, false)
                }
            }
        }

        return binding.root
    }

    inner class DialogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        init {
            Logger.v("어댑터 초기화!")
        }
        var items: List<Any> = listOf()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when (type) {
                DialogType.STORE_PICK -> {
                    Logger.v("지점이여~")
                    return DialogStorePickHolder(
                        ViewDialogItemStorePickBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
                DialogType.COMMON_SORT -> {
                    Logger.v("분류여~")
                    return DialogCommonSortHolder(
                        ViewDialogItemStorePickBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
                else -> {
                    return DialogStorePickHolder(
                        ViewDialogItemStorePickBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
        }


        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (type) {
                DialogType.STORE_PICK -> {
                    (holder as? DialogStorePickHolder)?.onBind()
                }
                DialogType.COMMON_SORT -> {
                    (holder as? DialogCommonSortHolder)?.onBind()
                }
            }
        }

        override fun getItemCount(): Int = items.size

        inner class DialogStorePickHolder(private val binding: ViewDialogItemStorePickBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = items[adapterPosition] as? StoreShopResponse.StorePick

                data?.let { it ->
                    name.text = it.relContNm

                    if (adapterPosition == storeSelected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    } else {
                        selector.isSelected = false
                        name.setTextColor(Color.parseColor("#2b2b2b"))
                    }

                    root.setOnClickListener {
                        if (storeSelected != adapterPosition) {
                            storeSelected = adapterPosition
                            EventBus.fire(StoreShopEvent(StoreShopEventType.STORE_CLICK, data))
                        }
                        dismiss()
                    }
                }

            }
        }

        inner class DialogCommonSortHolder(private val binding: ViewDialogItemStorePickBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = items[adapterPosition] as? String

                data?.let { it ->
                    name.text = it

                    if (adapterPosition == sortSelected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    }

                    root.setOnClickListener {
                        if (sortSelected != adapterPosition) {
                            sortSelected = adapterPosition
                            EventBus.fire(StoreShopEvent(StoreShopEventType.SORT_CLICK, data))
                        }
                        dismiss()
                    }
                }

            }
        }
    }
}