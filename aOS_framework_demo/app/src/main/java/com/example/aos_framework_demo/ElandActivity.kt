package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.adapter.CategoryIconAdapter
import com.example.aos_framework_demo.adapter.MainAdapter
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.ActivityElandBinding
import com.example.aos_framework_demo.databinding.ListCategoryBinding
import com.google.gson.Gson
import com.pionnet.overpass.customView.StickyHeaderItemDecoration
import com.pionnet.overpass.extension.getJsonFileToString
import java.util.*
import kotlin.collections.ArrayList

class ElandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityElandBinding
    private lateinit var headerBinding: ListCategoryBinding

    private lateinit var mainData: TestVO
    private var list: ArrayList<UiModel> = arrayListOf()

    private lateinit var mainAdapter: MainAdapter
    private lateinit var gridManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElandBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString: String = getJsonFileToString("test.json", this@ElandActivity)
        mainData = Gson().fromJson(jsonString, TestVO::class.java)

        headerBinding = ListCategoryBinding.inflate(layoutInflater, binding.stickyHeader, false)
        headerBinding.recyclerIcon.apply {
            adapter = CategoryIconAdapter(mainData.data.category)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addItemDecoration(StickyHeaderItemDecoration(stickyHeaderInterface))
        }

        binding.stickyHeader.addView(headerBinding.root)
        binding.stickyHeader.isVisible = true


        //todo adapter는 그리는 용도로만! data는 다른 곳에서 처리해서 넘겨준다, 클릭 이벤트 리스너도 여기서 넘겨주기
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
    }


    /**
     * 화면 그리는 MainAdapter
     */
    private fun setUI() {
        mainAdapter = MainAdapter(mainData.data, list)
        gridManager = GridLayoutManager(this@ElandActivity, 2)
        gridManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (list[position].tag == "category_list") {
                    return 1
                }
                return 2
            }
        }
        binding.mainRecycler.apply {
            adapter = mainAdapter
            layoutManager = gridManager
        }
    }

    private var stickyHeaderInterface: StickyHeaderItemDecoration.StickyHeaderInterface =
        object : StickyHeaderItemDecoration.StickyHeaderInterface {
            override fun getHeaderPositionForItem(itemPosition: Int): Int {
                return 0
            }

            override fun getHeaderLayout(headerPosition: Int): Int {
                return R.layout.list_category
            }

            override fun isHeader(itemPosition: Int): Boolean {
                return itemPosition == 0
            }

            override fun hideHeader() {
                binding.stickyHeader.isVisible = false
            }

            override fun showHeader() {
                binding.stickyHeader.isVisible = true
            }
        }
}