package com.cocokaribou.recycler_view_staggered_grid

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cocokaribou.recycler_view_staggered_grid.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var itemDeco: RecyclerView.ItemDecoration
    lateinit var mAdapter: MyAdapter
    var photoList = mutableListOf<PhotoVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getList()
    }

    private fun getList() {
        val myApi = MyApi.getApiService()
        val callBack: Call<ResponseBody> = myApi.getRandomPhotos()
        callBack.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("onFailure!", "통신실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("onResponse", "통신성공")
                val handler = Handler(Looper.getMainLooper())
                val jsonString = response.body()?.string()
                val jsonArray = JSONArray(jsonString)
                for (i in jsonArray.length()-1 downTo 0) {
                    val photo = Gson().fromJson(jsonArray[i].toString(), PhotoVO::class.java)
                    photoList.add(photo as PhotoVO)
                }

//                val goodsArrayString =
//                    jsonObj.getJSONObject("data").getJSONObject("goods_info").toString()
//                val bestVo = Gson().fromJson(goodsArrayString, PhotoVO::class.java)
//                initAdapter(bestVo.goodsList)
//                bestVo.goodsList.forEach {
//                }
                initAdapter(photoList)
            }
        })
    }

    private fun initAdapter(list: MutableList<PhotoVO>) {

        // pinterest ui
        val staggeredLayout = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        staggeredLayout.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.rvGoods.layoutManager = staggeredLayout
        binding.rvGoods.adapter = MyAdapter(list, this)
        binding.rvGoods.addOnScrollListener(ScrollListener(binding.rvGoods))

    }

    class ScrollListener(val rvGoods: RecyclerView) :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.e("onScrollStateChanged", "called")
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            Log.e("onScrolled", "called")
        }
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
