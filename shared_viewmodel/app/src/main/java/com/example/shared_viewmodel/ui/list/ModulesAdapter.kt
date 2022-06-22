package com.example.shared_viewmodel.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.shared_viewmodel.databinding.*
import com.example.shared_viewmodel.model.ModuleData
import com.example.shared_viewmodel.ui.list.viewholder.BannerHolder
import com.example.shared_viewmodel.ui.list.viewholder.GridListHolder
import com.example.shared_viewmodel.ui.list.viewholder.HorizontalListHolder
import com.example.shared_viewmodel.ui.list.viewholder.VerticalListHolder

/**
 * Modules Adapter
 * - adapter.setItemList
 */
class ModulesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ModulesType.values()[viewType]) {
            ModulesType.Banner -> {
                val itemBinding = VhMainHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                BannerHolder(itemBinding)
            }
            ModulesType.Grid -> {
                val itemBinding = VhGridListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridListHolder(itemBinding)
            }
            ModulesType.Vertical -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Horizontal -> {
                val itemBinding = VhHorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HorizontalListHolder(itemBinding)
            }
        }
    }

    private var onItemClickListener: Click = null

    fun setOnItemClickListener(listener: Click) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (holder is BaseHolder) {
            holder.bind(item, onItemClickListener)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].type.ordinal
    }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<ModuleData>() {
        override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean {
            return oldItem.storeList == newItem.storeList
        }
    })

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (holder is BaseHolder) {
            holder.onAppeared()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder is BaseHolder) {
            holder.onDisappeared()
        }
    }
}