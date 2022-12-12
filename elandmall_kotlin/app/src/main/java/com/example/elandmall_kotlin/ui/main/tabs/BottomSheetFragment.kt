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
import com.example.elandmall_kotlin.databinding.ViewDialogItemStorePickBinding
import com.example.elandmall_kotlin.model.PlanDetailResponse
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class DialogType {
    STORE_PICK,
    STORE_SHOP_SORT,
    PLAN_DETAIL_TAB,
    OTHER
}

var storeSelected = 0
var sortSelected = 1
var planDetailSelected = 0

class BottomSheetFragment(
    private val type: DialogType,
    private val list: List<Any>,
) :
    BottomSheetDialogFragment() {
    private val mAdapter by lazy { DialogAdapter().apply { items = list } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBottomSheetBinding.inflate(inflater).apply {
            dialogClose.setOnClickListener {
                dismiss()
            }
            dialogList.adapter = mAdapter
            dialogList.layoutManager = LinearLayoutManager(this@BottomSheetFragment.context, LinearLayoutManager.VERTICAL, false)

            when (type) {
                DialogType.STORE_PICK -> {
                    dialogTitle.text = "스토어픽 지점"
                }
                DialogType.STORE_SHOP_SORT -> {
                    dialogTitle.text = "상품분류"
                }
                DialogType.PLAN_DETAIL_TAB -> {
                    dialogTitle.text = "기획전 분류"
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
                DialogType.STORE_SHOP_SORT -> {
                    return DialogStorePickSortHolder(
                        ViewDialogItemStorePickBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
                DialogType.PLAN_DETAIL_TAB -> {
                    return DialogPlanDetailTabHolder(
                        ViewDialogItemPlanDetailBinding.inflate(
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
                DialogType.STORE_SHOP_SORT -> {
                    (holder as? DialogStorePickSortHolder)?.onBind()
                }
                DialogType.PLAN_DETAIL_TAB -> {
                    (holder as? DialogPlanDetailTabHolder)?.onBind()
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
                            EventBus.fire(
                                ViewHolderEvent(
                                    eventType = ViewHolderEventType.STORE_CLICK,
                                    tabType = TabType.STORE_SHOP,
                                    data
                                )
                            )
                        }
                        dismiss()
                    }
                }

            }
        }

        inner class DialogStorePickSortHolder(private val binding: ViewDialogItemStorePickBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = items[adapterPosition] as? String

                data?.let { it ->
                    name.text = it

                    if (adapterPosition == sortSelected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    } else {
                        selector.isSelected = false
                        name.setTextColor(Color.parseColor("#2b2b2b"))
                    }

                    root.setOnClickListener {
                        if (sortSelected != adapterPosition) {
                            sortSelected = adapterPosition
                            EventBus.fire(
                                ViewHolderEvent(
                                    eventType = ViewHolderEventType.SORT_CLICK,
                                    tabType = TabType.STORE_SHOP,
                                    data
                                )
                            )
                        }
                        dismiss()
                    }
                }

            }
        }

        inner class DialogPlanDetailTabHolder(private val binding: ViewDialogItemPlanDetailBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val data = items[adapterPosition] as? String
                data?.let { it ->
                    name.text = it

                    if (adapterPosition == planDetailSelected) {
                        selector.isSelected = true
                        name.setTextColor(resources.getColor(R.color.check_blue, null))
                    } else {
                        selector.isSelected = false
                        name.setTextColor(Color.parseColor("#2b2b2b"))
                    }

                    root.setOnClickListener {
                        if (planDetailSelected != adapterPosition) {
                            planDetailSelected = adapterPosition
                            EventBus.fire(
                                ViewHolderEvent(
                                    eventType = ViewHolderEventType.SORT_CLICK,
                                    tabType = TabType.PLAN_DETAIL,
                                    adapterPosition
                                )
                            )
                        }
                        dismiss()
                    }
                }

            }
        }
    }
}