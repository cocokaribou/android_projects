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
import com.pionnet.overpass.extension.*
import kotlin.collections.ArrayList

//class RecommendAdapter(private val data) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {
class RecommendAdapter(private val recList :ArrayList<TestVO.Data.Recommend>) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {

    private var count = 0
    class RecommendHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgView: ImageView = view.findViewById(R.id.img_recommend)
        val txtView: TextView = view.findViewById(R.id.txt_recommend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_store, parent, false)
        if(count >= recList.size){
            itemView.tag = "ADD"
            Log.e("⚠️+️", itemView.tag.toString())
        }else{
            itemView.tag = ""
            Log.e("⚠️-", itemView.tag.toString())
        }
        count++
        return RecommendHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        if(position<recList.size){
            Log.e("position", position.toString())
            var imgUrl = recList[position].imgUrl
            val storeNm = recList[position].storeName
            if (!imgUrl.isNullOrEmpty()) {
                imgUrl = "http:"+ imgUrl
                holder.imgView.loadImageCircle(imgUrl, 500, 500)
            }
            holder.txtView.text = storeNm
        }
    }

    override fun getItemCount(): Int { //recycler에서 한 칸 더 내줌
        return recList.size+1
    }

}