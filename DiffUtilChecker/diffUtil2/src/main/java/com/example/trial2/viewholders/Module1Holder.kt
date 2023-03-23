package com.example.trial2.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trial2.ModuleBaseHolder
import com.example.trial2.ModuleData
import com.example.trial2.databinding.ViewModule1Binding
import com.example.trial2.databinding.ViewModule1ItemBinding

class Module1Holder(private val binding: ViewModule1Binding) : ModuleBaseHolder(binding.root) {
    private val mAdapter by lazy { Module1InnerAdapter() }
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.Module1)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.Module1) = with(binding) {
        list.adapter = mAdapter
        title.text = data.title
        mAdapter.submitList(data.itemList)
    }

    class Module1InnerAdapter : ListAdapter<String, Module1InnerHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Module1InnerHolder(
                ViewModule1ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: Module1InnerHolder, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    class Module1InnerHolder(private val binding: ViewModule1ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.title.text = data
        }
    }
}