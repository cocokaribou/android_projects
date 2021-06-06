package com.example.aos_framework_demo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.ItemStoreBinding
import com.example.aos_framework_demo.databinding.ItemStoreEndBinding
import com.pionnet.overpass.extension.*
import kotlin.collections.ArrayList

//class RecommendAdapter(private val data) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {
class RecommendAdapter(private val recList: ArrayList<TestVO.Data.Recommend>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_LIST = 0
    private val TYPE_END = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_END -> {
                EndHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_store_end, parent, false)
                )
            }
            TYPE_LIST -> {
                StoreHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
                )
            }
            else->{
                EndHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_END -> {
                holder as EndHolder
            }
            TYPE_LIST -> {
                holder as StoreHolder
                var imgUrl = recList[position].imgUrl
                val storeNm = recList[position].storeName
                if (!imgUrl.isNullOrEmpty()) {
                    imgUrl = "http:" + imgUrl
                    holder.imgView.loadImageCircle(imgUrl, 500, 500)
                }
                holder.txtView.text = storeNm
            }
        }
    }

    //header, footer, item 구별
    override fun getItemViewType(position: Int): Int {
        if (position >= recList.size) {
            return TYPE_END
        }
        return TYPE_LIST
    }

    override fun getItemCount(): Int { //recycler에서 한 칸 더 내줌
        return recList.size + 1
    }


    class StoreHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreBinding.bind(view)
        val imgView: ImageView = binding.imgRecommend
        val txtView: TextView = binding.txtRecommend
    }

    class EndHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreEndBinding.bind(view)
    }
}