package com.example.openapi_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
import com.example.openapi_test.databinding.ItemBakeryBinding
import java.util.*
import kotlin.collections.ArrayList

class mAdapter() :
    RecyclerView.Adapter<mAdapter.mViewHolder>() {
//    private lateinit var list : ArrayList<DataVO.VoObject.Bakery>
    private val list = listOf(1,2,3,4,5,6,7,8)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mAdapter.mViewHolder {
        return mViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(list)
        //do something with list
    }

    override fun getItemCount(): Int {
        return 8
    }

    class mViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemBakeryBinding.bind(view)

        fun bind(list: ArrayList<DataVO.VoObject.Bakery>) {
            binding.txtStoreNm.text = list[adapterPosition].storeNm
            binding.txtAddress.text = list[adapterPosition].storeAdr
        }
        fun bind(list: List<Int>){
            binding.txtAddress.text = list[adapterPosition].toString()
        }
    }

    fun setData(dataList: ArrayList<DataVO.VoObject.Bakery>) {
//        this.list = dataList
        notifyDataSetChanged()
    }
}