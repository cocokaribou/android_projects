package com.example.aos_framework_demo.adapter

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.google.gson.Gson
import com.pionnet.overpass.extension.*
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

//class RecommendAdapter(private val data) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {
class RecommendAdapter(private val recList :ArrayList<TestVO.Data.Recommend>) : RecyclerView.Adapter<RecommendAdapter.RecommendHolder>() {

    class RecommendHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById(R.id.img_recommend)
        val txtView: TextView = view.findViewById(R.id.txt_recommend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommend, parent, false)
        return RecommendHolder(view)
    }

    override fun onBindViewHolder(holder: RecommendHolder, position: Int) {
        var imgUrl = recList[position].imgUrl
        val storeNm = recList[position].storeName
        if (!imgUrl.isNullOrEmpty()) {
            imgUrl = "http:"+ imgUrl
            holder.imgView.loadImageCircle(imgUrl, 500, 500)
        }
        holder.txtView.text = storeNm
    }

    override fun getItemCount(): Int {
        return recList.size
    }

}