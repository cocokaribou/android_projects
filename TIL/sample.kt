package com.example.openapi_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openapi_test.Data.DataVO
//import com.example.openapi_test.Data.Bakery
import com.example.openapi_test.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONException
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var youngAdapter: mAdapter
    val bakeryList = arrayListOf<DataVO.VoObject.Bakery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        requestApi()//api에서 정보 가져오기
    }

    private fun initAdapter() {
        youngAdapter = mAdapter()
        binding.recyclerViewMainList.apply {
            adapter = youngAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun requestApi() {
        val KEY: String = BuildConfig.API_KEY //com.google.secrets_gradle_plugin
        var domain: CharSequence =
            "http://openapi.seoul.go.kr:8088/(key)/json/LOCALDATA_072218_GS/(page)/600/"
        val keyPattern: Regex = "\\([key]*\\)".toRegex() //인증키 정규식
        var pagePattern: Regex = "\\([page]*\\)".toRegex() // 페이지 정규식

        //스크롤 마지막 단이 닿았을 때 page+1
        Thread {
            domain = domain.replace(keyPattern, KEY)
            domain = domain.replace(pagePattern, "1")
            try {
                val url = URL(domain as String)

                val streamReader = InputStreamReader(url.openStream())
                val reader = BufferedReader(streamReader)

                val buffer = StringBuffer()
                var line = reader.readLine()

                while (!line.isNullOrBlank()) {
                    buffer.append(line + "\n")
                    line = reader.readLine()
                }

                val jsonData = buffer.toString()
                //                    val obj = JSONObject(jsonData)

                //Gson 라이브러리로 처리
                val test = Gson().fromJson(jsonData, DataVO::class.java)
                test.voObject.bakeries {
                    if (it.storeState.contains("영업") && (!it.storeNm.contains("파리바게뜨") && (!it.storeNm.contains(
                            "뚜레쥬르"
                        )))
                    ) {
                        youngAdapter.setData(it)
                        Log.e(
                            "😋",
                            "${it.storeNm}, ${it.storeState}, ${it.storeTel}, ${it.storeAdr}"
                        )
                    }
                }

//                runOnUiThread {
//                    binding.recyclerViewMainList.apply {
//                        adapter = mAdapter(bakeryList)
//                        layoutManager = LinearLayoutManager(context)
//                    }
//                }
            } catch (e: MalformedURLException) {
                Log.e("exception", "$e")
            } catch (e: IOException) {
                Log.e("exception", "$e")
            } catch (e: JSONException) { //json 데이터를 잘못 다뤘을 경우
                Log.e("exception", "$e")
            } catch (e: NetworkOnMainThreadException) { //thread로 감싸주지 않았을 경우, 메인스레드에서 데이터통신 할 경우
                Log.e("exception", "$e")
            }
        }.start()



    }
}


