package com.example.trial2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.trial2.databinding.ViewModule1Binding
import com.example.trial2.databinding.ViewModule2Binding
import com.example.trial2.viewholders.Module1Holder
import com.example.trial2.viewholders.Module2Holder

class MainAdapter : ListAdapter<ModuleData, ModuleBaseHolder>(moduleDiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleBaseHolder {
        return when (viewType) {
            ModuleData.Module1.ordinal() ->
                Module1Holder(
                    ViewModule1Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ModuleData.Module2.ordinal() ->
                Module2Holder(
                    ViewModule2Binding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> Module1Holder(
                ViewModule1Binding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ModuleBaseHolder, position: Int) {
        holder.onBind(currentList[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).ordinal()
    }

    companion object {
        private val moduleDiffUtilCallback = object : DiffUtil.ItemCallback<ModuleData>() {
            override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean =
                when {
                    oldItem is ModuleData.Module1 && newItem is ModuleData.Module1 -> oldItem.id == newItem.id
                    oldItem is ModuleData.Module2 && newItem is ModuleData.Module2 -> oldItem.id == newItem.id
                    else -> oldItem.id == newItem.id
                }

            override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData): Boolean =
                when {
                    oldItem is ModuleData.Module1 && newItem is ModuleData.Module1 -> oldItem.id == newItem.id
                    oldItem is ModuleData.Module2 && newItem is ModuleData.Module2 -> oldItem.id == newItem.id
                    else -> oldItem == newItem
                }

            override fun getChangePayload(oldItem: ModuleData, newItem: ModuleData): Any? =
                when {
                    oldItem is ModuleData.Module1 && newItem is ModuleData.Module1 -> oldItem.id == newItem.id
                    oldItem is ModuleData.Module2 && newItem is ModuleData.Module2 -> oldItem.id == newItem.id
                    else -> super.getChangePayload(oldItem, newItem)
                }
        }
    }

    private inline fun <reified T : Any> T.ordinal(): Int {
        if (T::class.isSealed) {
            return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
        }

        val klass = if (T::class.nestedClasses.isEmpty()) {
            javaClass.declaringClass
        } else {
            javaClass
        }

        return klass.superclass?.classes?.indexOfFirst { it == klass } ?: -1
    }
}