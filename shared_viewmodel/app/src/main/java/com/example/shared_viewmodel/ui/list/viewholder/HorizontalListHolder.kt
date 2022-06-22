package com.example.shared_viewmodel.ui.list.viewholder

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared_viewmodel.databinding.VhHorizontalListBinding
import com.example.shared_viewmodel.model.ModuleData
import com.example.shared_viewmodel.ui.list.BaseHolder
import com.example.shared_viewmodel.ui.list.Click
import com.example.shared_viewmodel.ui.list.HorizontalAdapter

class HorizontalListHolder(private val itemBinding: VhHorizontalListBinding) : BaseHolder(itemBinding.root) {
    override fun bind(item: ModuleData, listener: Click) {
        item.storeList?.let { list ->
            with(itemBinding.rvHorizontal) {
                adapter = HorizontalAdapter(list, listener)
                layoutManager = LinearLayoutManager(itemBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
}