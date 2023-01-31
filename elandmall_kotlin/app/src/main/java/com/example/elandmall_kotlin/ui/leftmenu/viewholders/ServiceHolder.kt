package com.example.elandmall_kotlin.ui.leftmenu.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewLnbServiceBinding
import com.example.elandmall_kotlin.databinding.ViewLnbServiceItemBinding
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.LinkEvent
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuBaseViewHolder
import com.example.elandmall_kotlin.util.GridSpacingItemDecoration
import com.example.elandmall_kotlin.util.dpToPx

class ServiceHolder(val binding: ViewLnbServiceBinding) : LeftMenuBaseViewHolder(binding.root) {
    private val mAdapter by lazy { ServiceAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<LeftMenuResponse.NavCatServicemenu>()
            initUI(dataList)
        }
    }

    private fun initUI(data: List<LeftMenuResponse.NavCatServicemenu>) = with(binding) {
        mAdapter.submitList(data)

        list.adapter = mAdapter
        if (list.itemDecorationCount == 0) {
            list.addItemDecoration(GridSpacingItemDecoration(2, 1.dpToPx(), true))
        }
    }

    inner class ServiceAdapter : ListAdapter<LeftMenuResponse.NavCatServicemenu, ServiceAdapter.ServiceItemHolder>(object :
        DiffUtil.ItemCallback<LeftMenuResponse.NavCatServicemenu>() {
        override fun areItemsTheSame(
            oldItem: LeftMenuResponse.NavCatServicemenu,
            newItem: LeftMenuResponse.NavCatServicemenu
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: LeftMenuResponse.NavCatServicemenu,
            newItem: LeftMenuResponse.NavCatServicemenu
        ): Boolean = oldItem.serviceMenu == newItem.serviceMenu
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceItemHolder =
            ServiceItemHolder(
                ViewLnbServiceItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: ServiceItemHolder, position: Int) {
            holder.onBind()
        }

        inner class ServiceItemHolder(private val binding: ViewLnbServiceItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun onBind() = with(binding) {
                val item = currentList[adapterPosition]

                title.text = item.serviceMenu

                root.setOnClickListener {
                    EventBus.fire(LinkEvent(item.linkUrl))
                }
            }
        }
    }


}