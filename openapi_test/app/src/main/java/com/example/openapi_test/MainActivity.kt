package com.example.openapi_test

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var youngAdapter: mAdapter
    val bakeryList = arrayListOf<DataVO.VoObject.Bakery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        val api = BakeryAPI.create()
        api.getBakery(page = "1", total = "100").enqueue(object : Callback<DataVO> {
            override fun onResponse(call: Call<DataVO>, response: Response<DataVO>) {

                Log.e("success!", "yay")

                //Gson Convert
                if (response.isSuccessful) {
                    Gson().fromJson(
                        response.body()!!.toString(),
                        DataVO::class.java
                    )
                }
            }

            override fun onFailure(call: Call<DataVO>, t: Throwable) {
                //fail!
                Log.e("Fail!", "Fail")
            }
        })
    }

    private fun initAdapter() {
        youngAdapter = mAdapter()
        binding.recyclerViewMainList.apply {
            adapter = youngAdapter
            layoutManager = LinearLayoutManager(context)
        }
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


