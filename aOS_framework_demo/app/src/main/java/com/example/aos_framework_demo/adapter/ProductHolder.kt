package com.example.aos_framework_demo.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.databinding.ItemCategoryProductBinding

open class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {

    class ProductHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemCategoryProductBinding.bind(view)
        val imgProductImg: ImageView = binding.imgProductImg
        val imgStorePick = binding.imgStorePick
        val txtBrandNm: TextView = binding.txtBrandNm
        val txtProductNm: TextView = binding.txtProductNm
        val txtPrice: TextView = binding.txtPrice
        val txtSaleRate: TextView = binding.txtSaleRate
        val txtMarketPrice: TextView = binding.txtMarketPrice

        val reviewRow = binding.layoutReviewRow
        val imgStar1: ImageView = binding.imgStar1
        val imgStar2: ImageView = binding.imgStar2
        val imgStar3: ImageView = binding.imgStar3
        val imgStar4: ImageView = binding.imgStar4
        val imgStar5: ImageView = binding.imgStar5
        val txtReviewCount: TextView = binding.txtReviewCount

        val txtFreeShipping: TextView = binding.txtFreeShipping
    }
}