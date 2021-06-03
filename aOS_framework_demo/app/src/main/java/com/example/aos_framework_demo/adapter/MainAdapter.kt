package com.example.aos_framework_demo.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.*
import com.pionnet.overpass.customView.GridDividerItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration

class MainAdapter(
    private val mainData: TestVO.Data,
    private val list: ArrayList<UiModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FIRST = 1
    private val SECOND = 2
    private val THIRD = 3
    private val FOURTH = 4
    private val FIFTH = 5
    private val SIXTH = 6

    private var index = 0

    lateinit var recommendDeco: HorizontalSpacingItemDecoration
    lateinit var iconDeco: HorizontalSpacingItemDecoration
    lateinit var gridDeco: GridDividerItemDecoration

    private var viewCount = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIRST ->
                TitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_title, parent, false)
                )
            SECOND ->
                StoreHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_store, parent, false)
                )
            THIRD ->
                CategoryBestHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_category_best, parent, false)
                )
            FOURTH ->
                CategoryListHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_category, parent, false)
                )

            FIFTH ->
                CategoryTitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_category_title, parent, false)
                )
            SIXTH ->
                GoodsHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.list_goods, parent, false)
                )
            else ->
                TitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_title, parent, false)
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            FIRST -> {
                val titleHolder = holder as TitleHolder
            }
            SECOND -> {
                val listHolder = holder as StoreHolder
                listHolder.recyclerStore.apply {
                    adapter = RecommendAdapter(mainData.recommend)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (!this@MainAdapter::recommendDeco.isInitialized) {
                        recommendDeco = HorizontalSpacingItemDecoration(40, 60, true)
                        addItemDecoration(recommendDeco)
                    }
                }
            }
            THIRD -> {
                val categoryBestHolder = holder as CategoryBestHolder
            }
            FOURTH -> {
                val iconHolder = holder as CategoryListHolder
                iconHolder.iconList.apply {
                    adapter = CategoryIconAdapter(mainData.category)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (!this@MainAdapter::iconDeco.isInitialized) {
                        iconDeco = HorizontalSpacingItemDecoration(70, 55, true)
                        addItemDecoration(iconDeco)
                    }
                }
            }
            FIFTH -> {
                val categoryTitleHolder = holder as CategoryTitleHolder
                categoryTitleHolder.categoryTitle.text = mainData.category[index].ctgNm
            }
            SIXTH -> {
                val productHolder = holder as GoodsHolder
                productHolder.goodsList.apply {
                    adapter = CategoryProdAdapter(mainData.category[index].goodsList)
                    layoutManager = GridLayoutManager(context, 2)

                    if (!this@MainAdapter::gridDeco.isInitialized) {
                        gridDeco = GridDividerItemDecoration(1, Color.parseColor("#d4d4d4"))
                        addItemDecoration(gridDeco)
                    }

                }
            }
            else -> {
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        var tag: String = ""

        if (list.size > position)
            tag = list[position].tag

        return when (tag) {
            "recommend_title" -> FIRST
            "recommend_list" -> SECOND
            "category_best_title" -> THIRD
            "category_best_list" -> FOURTH
            "category_title" -> {
                index = list[position].position
                FIFTH
            }
            "category_list" -> {
                index = list[position].position
                SIXTH
            }
            else -> 0
        }
    }

    class TitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowTitleBinding.bind(view)
    }

    class StoreHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListStoreBinding.bind(view)
        val recyclerStore = binding.recyclerStore
    }

    class CategoryBestHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowCategoryBestBinding.bind(view)
    }

    class CategoryListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListCategoryBinding.bind(view)
        val iconList = binding.recyclerIcon
    }

    class CategoryTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowCategoryTitleBinding.bind(view)
        val categoryTitle = binding.txtTitle
    }

    class GoodsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListGoodsBinding.bind(view)
        val goodsList = binding.listGoods

    }
}