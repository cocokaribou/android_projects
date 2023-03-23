package com.example.openapi_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
import com.example.openapi_test.databinding.ItemBakeryBinding

class MainAdapter :
    ListAdapter<DataVO.VoObject.Bakery, MainAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBakeryBinding.bind(view)

        fun bind(data: DataVO.VoObject.Bakery) = with(binding){
            txtStoreNm.text = data.storeNm
            txtAddress.text = data.storeAdr
        }
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