package com.example.diffutilchecker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.diffutilchecker.databinding.ViewRecyclerHolder2Binding
import com.example.diffutilchecker.databinding.ViewRecyclerHolderBinding
import com.example.diffutilchecker.viewholders.Holder1
import com.example.diffutilchecker.viewholders.Holder2

class MainAdapter : ListAdapter<Data, BaseHolder>(object : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id

}) {
    /** 1. enum class - works! */
//    override fun getItemViewType(position: Int) = getItem(position).typeEnum.ordinal
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
//        return when (DATA_TYPE.values()[viewType]) {
//            DATA_TYPE.TYPE1 -> Holder1(ViewRecyclerHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            DATA_TYPE.TYPE2 -> Holder2(ViewRecyclerHolder2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
//        }
//    }

    /** 2. ordinal & companion objects - works! */
    override fun getItemViewType(position: Int) = getItem(position).ordinal()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return when (viewType) {
            Data.Data1.ordinal() -> Holder1(ViewRecyclerHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            Data.Data2.ordinal() -> Holder2(ViewRecyclerHolder2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> Holder2(ViewRecyclerHolder2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    /** 3. current item's type - doesn't works */
//    override fun getItemViewType(position: Int) = position
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
//        return when (getItem(viewType).typeEnum) {
//            DATA_TYPE.TYPE1 -> Holder1(ViewRecyclerHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
//            DATA_TYPE.TYPE2 -> Holder2(ViewRecyclerHolder2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
//        }
//    }




    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        Log.v("youngin", "onBind ${currentList[position].javaClass.simpleName} | ${holder.javaClass.simpleName}")
        holder.onBind(currentList[position], position)
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
