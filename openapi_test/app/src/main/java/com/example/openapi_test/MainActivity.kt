package com.example.openapi_test

import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import android.widget.AbsListView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
//import com.example.openapi_test.Data.Bakery
import com.example.openapi_test.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var bakeryList = arrayListOf<DataVO.VoObject.Bakery>()
    private val myAdapter = mAdapter()

    private var lastScrollY = 0
    private var mTotalScrolled = 0
    private var page = 0

    private val myListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            mTotalScrolled += dy

            if(mTotalScrolled<0){
                mTotalScrolled = 0
            }
            Log.e("늘어나나 봅세", "$mTotalScrolled")
            Log.e("dy", "$dy")
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.e("newState", "$newState")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        requestBakery(page)

//        binding.recyclerViewMainList.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//        }

    }

    private fun initAdapter() {
        binding.recyclerViewMainList.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(myListener)
        }
    }

    private fun requestBakery(scrollY: Int) {
        val bakeryApi = BakeryAPI.create()
        bakeryApi.getBakery(page = scrollY.toString(), total = "100")
            .enqueue(object : Callback<DataVO> {
                override fun onFailure(call: Call<DataVO>, t: Throwable) {
                    Log.e("Fail!", "Fail")
                }

                override fun onResponse(call: Call<DataVO>, response: Response<DataVO>) {
                    Log.e("Success!", "${response.code()}")

                    response.body()!!.voObject.bakeries.forEach {
                        bakeryList.add(it)
                        myAdapter.submitList(bakeryList)
                    }
                }
            })
    }

    /*
    retrofit의 annotation으로 path 입력
     */
//    private fun paging(page:String, total:String) {
//        var domain: CharSequence =
//            "http://openapi.seoul.go.kr:8088/(key)/json/LOCALDATA_072218_GS/(page)/(total)/"
//
//        val keyPattern: Regex = "\\([key]*\\)".toRegex() //인증키 정규식
//        var pagePattern: Regex = "\\([page]*\\)".toRegex() // 페이지 정규식
//        var totalPattern: Regex = "\\([total]*\\)".toRegex() // 페이지 토탈 정규식
//
//        //스크롤 마지막 단이 닿았을 때 page+1
//        domain.replace(keyPattern, replacement = KEY)
//        domain.replace(pagePattern, replacement = page)
//        domain.replace(totalPattern, replacement = total)
//
//        url = domain.toString()
//    }

}




