package com.example.shared_viewmodel.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.databinding.ItemGridListBinding
import com.example.shared_viewmodel.databinding.ItemHorizontalListBinding
import com.example.shared_viewmodel.databinding.ItemVerticalListBinding
import com.example.shared_viewmodel.model.StoreDataList

/**
 * Modules Adapter
 * - adapter.setItemList
 */

class ModulesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ModulesType.values()[viewType]) {
            ModulesType.Vertical -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Horizontal1 -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Grid -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Horizontal2 -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Horizontal3 -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (holder is VerticalListHolder) {
            holder.bind(item, onItemClickListener)
        }
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setItemList(list: MutableList<StoreDataList.StoreData>) {

    }


    class VerticalListHolder(private val itemBinding: ItemVerticalListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: String, listener: ((String) -> Unit)?) {
            itemBinding.itemContent = item
            itemBinding.root.setOnClickListener {
                listener?.let {
                    it(item)
                }
            }
            itemBinding.rb.rating = 100f
        }
    }


    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    })


    class HorizontalListHolder(itemBinding: ItemHorizontalListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: String, listener: ((String) -> Unit)?) {
        }
    }

    class GridListHolder(itemBinding: ItemGridListBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: String, listener: ((String) -> Unit)?) {

        }
    }
}