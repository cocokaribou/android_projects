package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.adapter.CategoryIconAdapter
import com.example.aos_framework_demo.adapter.MainAdapter
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.ActivityElandBinding
import com.example.aos_framework_demo.databinding.ListIconBinding
import com.google.gson.Gson
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.customView.StickyHeaderItemDecoration
import com.pionnet.overpass.extension.getDisplaySize
import com.pionnet.overpass.extension.getJsonFileToString
import java.util.*
import kotlin.collections.ArrayList

class ElandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityElandBinding
    private lateinit var headerBinding: ListIconBinding

    private lateinit var mainData: TestVO
    private var list: ArrayList<UiModel> = arrayListOf()
    private var titleList: ArrayList<Int> = arrayListOf()

    private lateinit var mainAdapter: MainAdapter
    private lateinit var categoryAdapter: CategoryIconAdapter

    private lateinit var gridManager: GridLayoutManager
    private lateinit var linearManager: LinearLayoutManager

    private lateinit var iconDeco: HorizontalSpacingItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestApi()
        setUI()

    }


    /**
     * json 데이터 & MainAdapter에 넘겨줄 tag와 position
     */
    private fun requestApi() {
        val jsonString: String = getJsonFileToString("test.json", this@ElandActivity)
        mainData = Gson().fromJson(jsonString, TestVO::class.java)

        list.add(UiModel("recommend_title"))
        list.add(UiModel("recommend_list"))
        list.add(UiModel("category_best_title"))
        list.add(UiModel("category_best_list"))

        mainData.data.category.forEachIndexed { i, ctg ->
            list.add(UiModel("category_title", i))
            ctg.goodsList.forEachIndexed() { j, _ ->
                list.add(UiModel("category_list", i, j))

            }
        }

        for (i in 0 until list.size) {
            if (list[i].tag == "category_title") {
                titleList.add(i)
            }
        }
    }

    private val onItemClickListener: (Any, Int, Int) -> Unit = { item, position, holderWidth ->
        if (item is TestVO.Data.Category) {
            categoryAdapter.selected(position)
            mainAdapter.setCategoryDataSetChanged()

            val width = getDisplaySize(this).widthPixels

            val index = titleList[position]
            linearManager.scrollToPositionWithOffset(position, width/2 - holderWidth/2)

            gridManager.scrollToPositionWithOffset(index, 0)
        }
    }

    /**
     * 화면 그리는 MainAdapter
     */
    private fun setUI() {
        mainAdapter = MainAdapter(mainData.data, list)
        gridManager = GridLayoutManager(this, 2)
        gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (list[position].tag == "category_list") {
                    return 1
                }
                return 2
            }
        }
        mainAdapter.setListener(onItemClickListener)

        binding.mainRecycler.apply {
            adapter = mainAdapter
            layoutManager = gridManager
            addItemDecoration(StickyHeaderItemDecoration(stickyHeaderInterface))

        }

        headerBinding = ListIconBinding.inflate(layoutInflater, binding.stickyHeader, false)
        binding.stickyHeader.addView(headerBinding.root)
        binding.stickyHeader.isVisible = false


        categoryAdapter = CategoryIconAdapter(mainData.data.category, onItemClickListener)
        linearManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        headerBinding.recyclerIcon.apply {
            adapter = categoryAdapter
            layoutManager = linearManager
            if (!this@ElandActivity::iconDeco.isInitialized) {
                iconDeco = HorizontalSpacingItemDecoration(75, 55, true)
                addItemDecoration(iconDeco)
            }
        }
    }

    private var stickyHeaderInterface: StickyHeaderItemDecoration.StickyHeaderInterface =
        object : StickyHeaderItemDecoration.StickyHeaderInterface {
            override fun getHeaderPositionForItem(itemPosition: Int): Int {
                return 0
            }

            override fun getHeaderLayout(headerPosition: Int): Int {
                return R.layout.list_icon
            }

            override fun isHeader(itemPosition: Int): Boolean {
                if (list[itemPosition].tag == "category_title" || list[itemPosition].tag == "category_list") {
                    return false
                }
                return true
            }

            override fun hideHeader() {
                binding.stickyHeader.isVisible = false
            }

            override fun showHeader() {
                binding.stickyHeader.isVisible = true
            }
        }

}