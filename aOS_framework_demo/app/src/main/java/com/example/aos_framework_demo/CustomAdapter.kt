package com.example.aos_framework_demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.databinding.ItemListBinding

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private val list = listOf(1,2,3,4,5,6,7,8,9,10)
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgView: ImageView = view.findViewById(R.id.item_img)
        val txtView: TextView = view.findViewById(R.id.item_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val string = "상품 ${list[position]}"
        holder.txtView.text = string
    }

    override fun getItemCount(): Int {
        return list.size
    }
}