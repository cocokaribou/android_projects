package com.example.elandmall_kotlin.ui.main.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    OTHER
}

var selected = 0

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
                    dialogList.layoutManager = GridLayoutManager(this@BottomSheetFragment.context, 2)
                }
            }
        }

        return binding.root
    }

    inner class DialogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var items: List<Any> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            when (type) {
                DialogType.STORE_PICK -> {
                    return DialogStorePickHolder(
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
            }
        }

        override fun getItemCount(): Int = items.size

        inner class DialogStorePickHolder(private val binding: ViewDialogItemStorePickBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = items[adapterPosition] as? StoreShopResponse.StorePick

                data?.let { it ->
                    name.text = data.relContNm

                    selector.isSelected = adapterPosition == selected

                    root.setOnClickListener {
                        if (selected != adapterPosition) {
                            selected = adapterPosition
                            notifyDataSetChanged()
                            EventBus.fire(StoreShopEvent(StoreShopEventType.STORE_CLICK, data))
                        }
                        dismiss()
                    }
                }

            }
        }
    }
}