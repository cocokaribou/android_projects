package com.example.diffutilchecker.viewholders

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilchecker.BaseHolder
import com.example.diffutilchecker.Data
import com.example.diffutilchecker.R
import com.example.diffutilchecker.databinding.ViewRecyclerHolder2Binding

class Holder2(private val binding: ViewRecyclerHolder2Binding) : BaseHolder(binding.root) {
    private val mAdapter by lazy { InnerAdapter2() }
    override fun onBind(data: Any, position: Int ) {
        (data as? Data.Data2)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: Data.Data2) = with(binding){
        list2.adapter = mAdapter
        mAdapter.submitList(data.itemList)
        (list2.layoutManager as? LinearLayoutManager)?.scrollToPosition(1)
    }

    inner class InnerAdapter2 : ListAdapter<String, InnerHolder2>(object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem.equals(newItem, false)

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            InnerHolder2(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recylcer2, parent, false)
            )

        override fun onBindViewHolder(holder: InnerHolder2, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    inner class InnerHolder2(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: String) {
            itemView.findViewById<TextView>(R.id.title).text = data
        }
    }
}