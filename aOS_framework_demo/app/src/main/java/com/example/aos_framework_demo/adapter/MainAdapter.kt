package com.example.aos_framework_demo.adapter

import android.icu.text.CaseMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.*

class MainAdapter(private val mainData: TestVO.Data, private val list: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val FIRST = 1
    val SECOND = 2
    val THIRD = 3
    val FOURTH = 4
    val FIFTH = 5
    val SIXTH = 6
    val SEVENTH = 7


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIRST ->
                TitleHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.title, parent, false)
                )
            SECOND ->
                ListHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.list, parent, false)
                )
            THIRD ->
                CategoryBestTitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_best_title, parent, false)
                )
            FOURTH ->
                CategoryBestListHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_best_list, parent, false)
                )

            FIFTH ->
                CategoryTitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_title, parent, false)
                )
            SIXTH ->
                ProductHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.product, parent, false)
                )
            else ->
                TitleHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.title, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FIRST -> {
                val titleHolder = holder as TitleHolder
                titleHolder.binding.title.text = "추천 지점"
            }
            SECOND -> {
                mainData.recommend.forEach {
                    Log.e("2️⃣", it.imgUrl + " " + it.storeName)
                }
            }
            THIRD -> {
                Log.e("3️⃣", "카테고리별 베스트 상품")
            }
            FOURTH -> {
                mainData.category.forEach {
                    Log.e("4️⃣️", it.dactiveImgUrl + " " + it.ctgNm)
                }
            }
            FIFTH -> {
                Log.e("5️⃣", mainData.category[position-1].ctgNm)
            }
            SIXTH -> {
                mainData.category[position-1].goodsList.forEach {
                    Log.e("6️⃣", it.goodsNm)
                }
            }
            else -> {Log.e("⚠️", "else")}
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        var tag: String = ""

        if (list.size > position)
            tag = list[position]

        return when (tag) {
            "recommend_title" -> FIRST
            "recommend_list" -> SECOND
            "category_best_title" -> THIRD
            "category_best_list" -> FOURTH
            "category_title" -> FIFTH
            "category_list" -> SIXTH
            else -> 0
        }
    }

    class TitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TitleBinding.bind(view)
    }

    class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListBinding.bind(view)
        val list = binding.listRecommendstore
    }

    class CategoryBestTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CategoryBestTitleBinding.bind(view)
    }

    class CategoryBestListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CategoryBestListBinding.bind(view)
    }

    class CategoryTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CategoryTitleBinding.bind(view)
    }

    class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ProductBinding.bind(view)

    }
}