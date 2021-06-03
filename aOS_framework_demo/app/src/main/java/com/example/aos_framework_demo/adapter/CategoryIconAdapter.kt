package com.example.aos_framework_demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.ItemCategoryBinding
import com.pionnet.overpass.extension.*

class CategoryIconAdapter(private val ctgList: ArrayList<TestVO.Data.Category>) :
    RecyclerView.Adapter<CategoryIconAdapter.CategoryHolder>() {

    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryBinding.bind(view)
        val imgView: ImageView = binding.imgCtgIcon
        val textView: TextView = binding.txtCtgNm
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        var imgUrl = ctgList[position].dactiveImgUrl
        val ctgNm = ctgList[position].ctgNm
        with(holder) {
            if (!imgUrl.isNullOrEmpty()) {
                imgUrl = "http:" + imgUrl
                imgView.loadImage(imgUrl)
            }
            textView.text = ctgNm
        }
    }

    override fun getItemCount(): Int {
        return ctgList.size
    }
}