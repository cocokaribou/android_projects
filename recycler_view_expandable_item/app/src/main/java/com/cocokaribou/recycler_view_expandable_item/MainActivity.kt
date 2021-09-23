package com.cocokaribou.recycler_view_expandable_item

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cocokaribou.recycler_view_expandable_item.databinding.ActivityMainBinding
import com.cocokaribou.recycler_view_expandable_item.databinding.ItemGoodsDetailBinding
import com.google.gson.Gson
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var transformationLayout: ItemGoodsDetailBinding
    lateinit var itemDeco: RecyclerView.ItemDecoration

    val myClickListener: (BestVO.Goods) -> Unit = { item ->
        Log.e("item clicked", "click!")
        transformationLayout = ItemGoodsDetailBinding.inflate(layoutInflater)
        startTransformActivity(this, transformationLayout.root)
    }
    var mAdapter = MyAdapter(myClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        getList()
    }

    private fun getList() {
        val myApi = MyApi.getApiService()
        // 패션의류
        val callBack: Call<ResponseBody> = myApi.getBestProducts("1703314378")
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
        if(!this@MainActivity::itemDeco.isInitialized){
            itemDeco = GridSpacingItemDecoration(2, 0, false)
            binding.rvGoods.addItemDecoration(itemDeco)
        }else{
            binding.rvGoods.removeItemDecoration(itemDeco)
        }
    }

    companion object {
        fun startTransformActivity(
            context: Context,
            transformationLayout: TransformationLayout
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }


    class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
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
