package com.example.openapi_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
import com.example.openapi_test.databinding.ItemBakeryBinding
import java.util.*
import kotlin.collections.ArrayList

class mAdapter() :
//    RecyclerView.Adapter<mAdapter.mViewHolder>() {
    ListAdapter<DataVO.VoObject.Bakery, mAdapter.mViewHolder>(diffUtil) {
    private var bakeryList= emptyList<DataVO.VoObject.Bakery>() //여기를
    private val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mAdapter.mViewHolder {
        return mViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(bakeryList[position])
    }

    override fun getItemCount(): Int {
        return bakeryList.size
    }

    class mViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemBakeryBinding.bind(view)


        fun bind(data: DataVO.VoObject.Bakery) {
            binding.txtStoreNm.text = data.storeNm
            binding.txtAddress.text = data.storeAdr
        }

        fun bind(list: List<Int>) {
            binding.txtAddress.text = list[adapterPosition].toString()
        }
    }


    override fun submitList(list: MutableList<DataVO.VoObject.Bakery>?) {
        bakeryList = list!!
        super.submitList(list)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DataVO.VoObject.Bakery>() {
            override fun areContentsTheSame(
                oldItem: DataVO.VoObject.Bakery,
                newItem: DataVO.VoObject.Bakery
            ): Boolean {
                return oldItem.storeNm == newItem.storeNm
                        || oldItem.storeAdr == newItem.storeAdr
            }

            override fun areItemsTheSame(
                oldItem: DataVO.VoObject.Bakery,
                newItem: DataVO.VoObject.Bakery
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}