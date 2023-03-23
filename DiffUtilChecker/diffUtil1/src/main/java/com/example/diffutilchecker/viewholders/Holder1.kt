package com.example.diffutilchecker.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilchecker.BaseHolder
import com.example.diffutilchecker.Data
import com.example.diffutilchecker.R
import com.example.diffutilchecker.databinding.ViewRecyclerHolderBinding

var counter = 0

class Holder1(private val binding: ViewRecyclerHolderBinding) : BaseHolder(binding.root) {
    private val mAdapter by lazy { InnerAdapter1() }
    lateinit var mData: Data.Data1
    override fun onBind(data: Any, position: Int) {
        (data as? Data.Data1)?.let {
            mData = it
            initUI()
        }
    }

    private fun initUI() = with(binding){
        list1.adapter = mAdapter

        mAdapter.submitList(mData.itemList)
    }

    inner class InnerAdapter1 : ListAdapter<String, InnerHolder1>(object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem.equals(newItem, false)
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            InnerHolder1(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recylcer, parent, false)
            )

        override fun onBindViewHolder(holder: InnerHolder1, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    inner class InnerHolder1(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: String) {
            itemView.findViewById<TextView>(R.id.title).text = data
        }
    }
}