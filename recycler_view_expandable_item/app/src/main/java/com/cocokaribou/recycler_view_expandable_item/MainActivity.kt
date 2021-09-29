package com.cocokaribou.recycler_view_expandable_item

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocokaribou.recycler_view_expandable_item.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationStartContainer
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var itemDeco: RecyclerView.ItemDecoration
    var mAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        getList()

        binding.btnToView.setOnClickListener {
            binding.btnTransformationLayout.startTransform()
        }
        binding.cardviewPopup.setOnClickListener {
            binding.btnTransformationLayout.finishTransform()
        }
    }

    private fun getList() {
        val myApi = MyApi.getApiService()
        // 잡화 슈즈
        val callBack: Call<ResponseBody> = myApi.getBestProducts("1607006212")
        callBack.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("onFailure!", "통신실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("onResponse", "통신성공")
                val jsonString = response.body()?.string()
                val jsonObj = JSONObject(jsonString)
                val goodsArrayString =
                    jsonObj.getJSONObject("data").getJSONObject("goods_info").toString()
                val bestVo = Gson().fromJson(goodsArrayString, BestVO::class.java)
                mAdapter.submitList(bestVo.goodsList)
            }

        })
    }

    private fun initAdapter() {
        binding.rvGoods.adapter = mAdapter
        binding.rvGoods.layoutManager = GridLayoutManager(this, 2)
        Log.e("layout param", "${binding.rvGoods.layoutParams}")
        if (binding.rvGoods.itemDecorationCount > 0) {
            binding.rvGoods.removeItemDecoration(itemDeco)
        } else {
            itemDeco = GridSpacingItemDecoration(2, 0, false)
            binding.rvGoods.addItemDecoration(itemDeco)
        }


        // veil recycler
//        binding.veilRecyclerView.setAdapter(mAdapter)
//        binding.veilRecyclerView.setLayoutManager(GridLayoutManager(this, 2))
//        binding.veilRecyclerView.addVeiledItems(6)
//        binding.veilRecyclerView.veil()

    }

    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }
}
