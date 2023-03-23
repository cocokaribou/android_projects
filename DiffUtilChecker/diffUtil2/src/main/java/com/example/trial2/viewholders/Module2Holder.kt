package com.example.trial2.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trial2.ModuleBaseHolder
import com.example.trial2.ModuleData
import com.example.trial2.databinding.ViewModule1ItemBinding
import com.example.trial2.databinding.ViewModule2Binding
import com.example.trial2.databinding.ViewModule2ItemBinding

class Module2Holder(private val binding: ViewModule2Binding) : ModuleBaseHolder(binding.root) {
    private val mAdapter by lazy { Module2InnerAdapter() }
    override fun onBind(data: Any, position: Int) {
        (data as? ModuleData.Module2)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.Module2) = with(binding) {
        list.adapter = mAdapter
        title.text = data.title
        mAdapter.submitList(data.itemList)
    }

    class Module2InnerAdapter : ListAdapter<String, Module2InnerHolder>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Module2InnerHolder(
                ViewModule2ItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: Module2InnerHolder, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    class Module2InnerHolder(private val binding: ViewModule2ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.title.text = data
        }
    }
}