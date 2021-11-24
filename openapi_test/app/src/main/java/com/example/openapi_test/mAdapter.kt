package com.example.openapi_test

import android.util.Log
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
//    private var bakeryList= emptyList<DataVO.VoObject.Bakery>()
    private var bakeryList : MutableList<DataVO.VoObject.Bakery> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mAdapter.mViewHolder {
        Log.e("생성됨", "$viewType")
        return mViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        Log.e("그려짐", "$position")
        holder.bind(bakeryList[position])
    }

    override fun getItemCount(): Int {
        return bakeryList.size
    }

    class mViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBakeryBinding.bind(view)

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
                        && oldItem.storeAdr == newItem.storeAdr
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