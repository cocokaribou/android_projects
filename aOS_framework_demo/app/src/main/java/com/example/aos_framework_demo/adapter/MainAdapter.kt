package com.example.aos_framework_demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.*
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.extension.loadImageWithScale
import com.pionnet.overpass.extension.priceFormat
import com.pionnet.overpass.extension.setPriceStroke

class MainAdapter(
    private val mainData: TestVO.Data,
    private val list: ArrayList<UiModel>,
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

    private lateinit var categoryIconAdapter: CategoryIconAdapter
    private lateinit var itemCategoryLinear: LinearLayoutManager
    lateinit var onItemClickListener: (Any, Int, Int) -> Unit


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
                CategoryIconHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_icon, parent, false)
                )
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
                    adapter = RecommendAdapter(mainData.recommend, context)
                    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                    if (!this@MainAdapter::recommendDeco.isInitialized) {
                        recommendDeco = HorizontalSpacingItemDecoration(40, 55, true)
                        addItemDecoration(recommendDeco)
                    }
                }
            }
            THIRD -> {
                holder as CategoryBestHolder
            }
            FOURTH -> {
                holder as CategoryIconHolder
                categoryIconAdapter = CategoryIconAdapter(mainData.category, onItemClickListener)
                itemCategoryLinear = LinearLayoutManager(
                    holder.itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                holder.iconList.apply {
                    adapter = categoryIconAdapter
                    layoutManager = itemCategoryLinear

                    if (!this@MainAdapter::iconDeco.isInitialized) {
                        iconDeco = HorizontalSpacingItemDecoration(70, 55, true)
                        addItemDecoration(iconDeco)
                    }
                }

                categoryIconAdapter.selected(0)
            }
            FIFTH -> {
                holder as CategoryTitleHolder
                holder.bind(mainData.category[ctgIndex].ctgNm)
            }
            SIXTH -> {
                holder as GoodsHolder
                holder.bind(mainData.category[ctgIndex].goodsList[goodsIndex])
            }
            else -> {
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        var tag = ""

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

    class CategoryIconHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListIconBinding.bind(view)
        val iconList = binding.recyclerIcon

    }

    class CategoryTitleHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RowCategoryTitleBinding.bind(view)
        private val categoryTitle = binding.txtTitle
        fun bind(title: String) {
            categoryTitle.text = title
        }
    }

    class GoodsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemGoodsBinding.bind(view)
        private val imgProductImg: ImageView = binding.imgProductImg
        private val imgStorePick = binding.imgStorePick
        private val txtBrandNm: TextView = binding.txtBrandNm
        private val txtProductNm: TextView = binding.txtProductNm
        private val txtPrice: TextView = binding.txtPrice
        private val txtSaleRate: TextView = binding.txtSaleRate
        private val txtMarketPrice: TextView = binding.txtMarketPrice

        private val reviewRow = binding.layoutReviewRow
        private val txtReviewCount: TextView = binding.txtReviewCount
        private val txtFreeShipping: TextView = binding.txtFreeShipping

        fun bind(item: TestVO.Data.Category.Goods) {
            with(item) {
                var imgUrl = "http:${imageUrl}"
                imgProductImg.loadImageWithScale(imgUrl, 450, 450)

                txtBrandNm.text = brandNm
                txtProductNm.text = goodsNm

                txtSaleRate.visibility = if (saleRate == 0) View.GONE else View.VISIBLE
                txtSaleRate.text = "${saleRate}%"

                txtPrice.text = "${priceFormat(custSalePrice.toString())}원"

                txtMarketPrice.visibility = if (marketPrice == 0) View.GONE else View.VISIBLE
                txtMarketPrice.text = "${priceFormat(marketPrice.toString())}원"
                txtMarketPrice.setPriceStroke(10, true)

                reviewRow.visibility = if (goodsCommentCount == 0) View.GONE else View.VISIBLE

                txtReviewCount.text = "리뷰 (${goodsCommentCount})"

                txtFreeShipping.visibility = if (iconView.isEmpty()) View.GONE else View.VISIBLE
                imgStorePick.visibility = if (fieldRecevPossYn == "N") View.GONE else View.VISIBLE

            }
        }
    }

    fun setListener(onItemClickListener: (Any, Int, Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    fun setCategoryDataSetChanged() {
        categoryIconAdapter.notifyDataSetChanged()
    }
}