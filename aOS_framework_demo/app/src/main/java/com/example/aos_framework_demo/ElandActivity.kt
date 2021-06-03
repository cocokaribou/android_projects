package com.example.aos_framework_demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.adapter.CategoryIconAdapter
import com.example.aos_framework_demo.adapter.CategoryProdAdapter
import com.example.aos_framework_demo.adapter.MainAdapter
import com.example.aos_framework_demo.adapter.RecommendAdapter
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.data.UiModel
import com.example.aos_framework_demo.databinding.ActivityElandBinding
import com.google.gson.Gson
import com.pionnet.overpass.customView.GridDividerItemDecoration
import com.pionnet.overpass.customView.GridSpacingItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.extension.getJsonFileToString
import java.util.*
import kotlin.collections.ArrayList

class ElandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityElandBinding

    private lateinit var mainData: TestVO
    private var list: ArrayList<UiModel> = arrayListOf()

    private lateinit var mainAdapter: MainAdapter
    private lateinit var linearManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElandBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        mainData.data.category.forEachIndexed { i, _ ->
            list.add(UiModel("category_title", i))
            list.add(UiModel("category_list", i))

        }
    }


    /**
     * 화면 그리는 MainAdapter
     */
    private fun setUI() {
        mainAdapter = MainAdapter(mainData.data, list)
        linearManager = LinearLayoutManager(this@ElandActivity)
        binding.mainRecycler.apply {
            adapter = mainAdapter
            layoutManager = linearManager
        }
    }
}