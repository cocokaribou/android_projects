package com.example.sampleapp

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapp.databinding.ItemNormalBinding

class NormalHolder(private var itemBinding: ItemNormalBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(dataList: MutableList<String>, position: Int) {
        with(itemBinding) {
            Glide.with(root.context)
                .load(dataList[position])
                .into(img)
        }
    }
}