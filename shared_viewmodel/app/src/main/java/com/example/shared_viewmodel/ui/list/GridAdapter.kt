package com.example.shared_viewmodel.ui.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.LogHelper
import com.example.shared_viewmodel.databinding.ItemGridBinding
import com.example.shared_viewmodel.model.StoreData

class GridAdapter(private val clickListener: ((String) -> Unit)?) :
    RecyclerView.Adapter<GridAdapter.GridHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        return GridHolder(ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        holder.bind(differ.currentList[position], clickListener)
    }

    override fun getItemCount(): Int = differ.currentList.size

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<StoreData>() {
        override fun areItemsTheSame(oldItem: StoreData, newItem: StoreData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StoreData, newItem: StoreData): Boolean {
            return oldItem.stoName == newItem.stoName
        }
    })

    class GridHolder(private val itemBinding: ItemGridBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: StoreData, listener: ((String) -> Unit)?) = with(itemBinding){
            if (item.stoNo == 0 && item.stoName == "") {
                viewEmptyHolder.visibility = View.VISIBLE
            } else {
                viewEmptyHolder.visibility = View.GONE
                itemContent = item.stoName

                root.apply {
                    setOnClickListener {
                        listener?.let {
                            it(item.stoName!!)
                        }
                    }
                }
            }
        }
    }
}
