package com.example.aos_framework_demo.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.ElandActivity
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.*
import com.pionnet.overpass.customView.GridDividerItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.customView.StickyHeaderItemDecoration
import com.pionnet.overpass.extension.loadImageWithScale
import com.pionnet.overpass.extension.priceFormat
import com.pionnet.overpass.extension.setPriceStroke

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

    private var ctgIndex = 0
    private var goodsIndex = 0

    lateinit var recommendDeco: HorizontalSpacingItemDecoration
    lateinit var iconDeco: HorizontalSpacingItemDecoration

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
            FOURTH -> {
                CategoryListHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_category, parent, false)
                )
            }

            FIFTH ->
                CategoryTitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_category_title, parent, false)
                )
            SIXTH ->
                GoodsHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false)
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
                holder as TitleHolder
            }
            SECOND -> {
                holder as StoreHolder
                holder.recyclerStore.apply {
                    adapter = RecommendAdapter(mainData.recommend)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (!this@MainAdapter::recommendDeco.isInitialized) {
                        recommendDeco = HorizontalSpacingItemDecoration(40, 60, true)
                        addItemDecoration(recommendDeco)
                    }
                }
            }
            THIRD -> {
                holder as CategoryBestHolder
            }
            FOURTH -> {
                holder as CategoryListHolder
                holder.iconList.apply {
                    adapter = CategoryIconAdapter(mainData.category)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (!this@MainAdapter::iconDeco.isInitialized) {
                        iconDeco = HorizontalSpacingItemDecoration(70, 55, true)
                        addItemDecoration(iconDeco)
                    }
                }
            }
            FIFTH -> {
                holder as CategoryTitleHolder
                val title = mainData.category[ctgIndex].ctgNm
                holder.categoryTitle.text = title
            }
            SIXTH -> {
                holder as GoodsHolder
                with(mainData.category[ctgIndex].goodsList[goodsIndex]) {
                    var imgUrl = "http:" + imageUrl
                    holder.imgProductImg.loadImageWithScale(imgUrl, 450, 450)

                    holder.txtBrandNm.text = brandNm
                    holder.txtProductNm.text = goodsNm

                    if (saleRate == 0) {
                        holder.txtSaleRate.visibility = View.GONE
                    }
                    holder.txtSaleRate.text = saleRate.toString() + "%"
                    holder.txtPrice.text = priceFormat(custSalePrice.toString()) + "원"

                    if (marketPrice == 0) {
                        holder.txtMarketPrice.visibility = View.GONE
                    }
                    holder.txtMarketPrice.text = priceFormat(marketPrice.toString()) + "원"
                    holder.txtMarketPrice.setPriceStroke(10, true)

                    if (goodsCommentCount == 0) {
                        holder.reviewRow.visibility = View.GONE
                    }
                    holder.txtReviewCount.text = "리뷰 (" + goodsCommentCount.toString() + ")"

                    if (iconView.isEmpty()) {
                        holder.txtFreeShipping.visibility = View.GONE
                    }
                    if (fieldRecevPossYn == "N") {
                        holder.imgStorePick.visibility = View.GONE
                    }
                }
//                holder.goodsList.apply {
//                    adapter = CategoryProdAdapter(mainData.category[ctgIndex].goodsList)
//                    layoutManager = GridLayoutManager(context, 2)
//
//                    if (!this@MainAdapter::gridDeco.isInitialized) {
//                        gridDeco = GridDividerItemDecoration(1, Color.parseColor("#d4d4d4"))
//                        addItemDecoration(gridDeco)
//                    }
//
//                }
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
                ctgIndex = list[position].ctgPosition
                FIFTH
            }
            "category_list" -> {
                ctgIndex = list[position].ctgPosition
                goodsIndex = list[position].goodsPosition
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

//    class GoodsHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val binding = ListGoodsBinding.bind(view)
//        val goodsList = binding.listGoods
//
//    }

    class GoodsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGoodsBinding.bind(view)
        val imgProductImg: ImageView = binding.imgProductImg
        val imgStorePick = binding.imgStorePick
        val txtBrandNm: TextView = binding.txtBrandNm
        val txtProductNm: TextView = binding.txtProductNm
        val txtPrice: TextView = binding.txtPrice
        val txtSaleRate: TextView = binding.txtSaleRate
        val txtMarketPrice: TextView = binding.txtMarketPrice

        val reviewRow = binding.layoutReviewRow
        val txtReviewCount: TextView = binding.txtReviewCount
        val txtFreeShipping: TextView = binding.txtFreeShipping
    }
}