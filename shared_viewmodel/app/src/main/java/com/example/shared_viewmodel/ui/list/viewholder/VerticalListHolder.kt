package com.example.shared_viewmodel.ui.list.viewholder

import com.example.shared_viewmodel.databinding.ItemVerticalListBinding
import com.example.shared_viewmodel.model.ModuleData
import com.example.shared_viewmodel.ui.list.BaseHolder
import com.example.shared_viewmodel.ui.list.Click

class VerticalListHolder(private val itemBinding: ItemVerticalListBinding) : BaseHolder(itemBinding.root) {
    override fun bind(item: ModuleData, listener: Click) {
        item.store?.let { item ->
            itemBinding.itemContent = item.stoName
            itemBinding.root.setOnClickListener {
                listener?.let {
                    it(item.stoName!!)
                }
            }
            itemBinding.rb.rating = 100f
        }
    }
}