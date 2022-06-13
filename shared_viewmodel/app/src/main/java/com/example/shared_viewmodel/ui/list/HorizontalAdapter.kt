package com.example.shared_viewmodel.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.databinding.ItemHorizontalBinding
import com.example.shared_viewmodel.model.StoreData

class HorizontalAdapter(val list: List<StoreData>, private val clickListener: ((String) -> Unit)?) :
    RecyclerView.Adapter<HorizontalAdapter.HorizontalHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalHolder {
        return HorizontalHolder(ItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HorizontalHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    override fun getItemCount(): Int = list.size

    class HorizontalHolder(private val itemBinding: ItemHorizontalBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: StoreData, listener: ((String) -> Unit)?) {
            itemBinding.itemContent = item.stoName
            itemBinding.root.setOnClickListener {
                listener?.let {
                    it(item.stoName!!)
                }
            }
        }
    }
}