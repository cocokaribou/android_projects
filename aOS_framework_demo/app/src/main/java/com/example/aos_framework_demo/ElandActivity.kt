package com.example.aos_framework_demo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.adapter.CategoryIconAdapter
import com.example.aos_framework_demo.adapter.CategoryProdAdapter
import com.example.aos_framework_demo.adapter.RecommendAdapter
import com.example.aos_framework_demo.data.TestVO
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
    private lateinit var rAdapter: RecommendAdapter
    private lateinit var cAdapter: CategoryIconAdapter
    private lateinit var cAdapter2: CategoryProdAdapter
    private lateinit var linearManager: LinearLayoutManager
    private lateinit var linearManager2: LinearLayoutManager
    private lateinit var gridManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElandBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //todo adapter는 그리는 용도로만! data는 다른 곳에서 처리해서 넘겨준다, 클릭 이벤트 리스너도 여기서 넘겨주기
        /**
         * json 데이터
         */
        val jsonString: String = getJsonFileToString("test.json", this@ElandActivity)
        val obj: TestVO = Gson().fromJson(jsonString, TestVO::class.java)
        val recList: ArrayList<TestVO.Data.Recommend> = obj.data.recommend
        val ctgList: ArrayList<TestVO.Data.Category> = obj.data.category

        /**
         * 추천지점 recycler view
         */
        rAdapter = RecommendAdapter(recList)
        linearManager = LinearLayoutManager(this@ElandActivity)
        linearManager.orientation = LinearLayoutManager.HORIZONTAL

        val recyclerRecommend = binding.listRecommendstore
        recyclerRecommend.adapter = rAdapter
        recyclerRecommend.layoutManager = linearManager

        val linearDeco = HorizontalSpacingItemDecoration(30, 30, true)
        recyclerRecommend.addItemDecoration(linearDeco)


        /**
         * 카테고리 아이콘 recycler view
         */
        cAdapter = CategoryIconAdapter(ctgList)
        linearManager2 = LinearLayoutManager(this@ElandActivity)
        linearManager2.orientation = LinearLayoutManager.HORIZONTAL

        val recyclerCategory = binding.listCtgIcons
        recyclerCategory.adapter = cAdapter
        recyclerCategory.layoutManager = linearManager2

        val linearDeco2 = HorizontalSpacingItemDecoration(70, 60, true)
        recyclerCategory.addItemDecoration(linearDeco2)

        /**
         * 카테고리별 베스트 상품 recycler view
         * 어댑터에 배열 요소 하나씩만 전달해서 뿌려주기 <-이거 아님 <-이거 맞음
         */

        ctgList.forEachIndexed() { i, e ->
            val categoryTitle = ctgList[i].ctgNm
            binding.txtCategoryName.text = categoryTitle

            val prodList: ArrayList<TestVO.Data.Category.Goods> = ctgList[i].goodsList

            cAdapter2 = CategoryProdAdapter(prodList)
            gridManager = GridLayoutManager(this@ElandActivity, 2)

            val recyclerCategoryProd = binding.listCtgProd
            recyclerCategoryProd.adapter = cAdapter2
            recyclerCategoryProd.layoutManager = gridManager

            val gridDeco = GridDividerItemDecoration(1, 1, Color.parseColor("#dedede"))
            recyclerCategoryProd.addItemDecoration(gridDeco)
        }
    }
}