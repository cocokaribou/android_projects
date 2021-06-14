package com.example.openapi_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
//import com.example.openapi_test.Data.Bakery
import com.example.openapi_test.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONException
import java.io.*
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val KEY: String = "demo" //TODO 하드코딩된 api key
    private var domain: CharSequence =
        "http://openapi.seoul.go.kr:8088/(인증키)/json/LOCALDATA_072218_GS/1/600/"
    private val pattern: Regex = "\\([^)]*\\)".toRegex() //괄호안 정규식

    lateinit var binding: ActivityMainBinding
    lateinit var mainRecycler: RecyclerView
    lateinit var mainAdapter: mAdapter
    lateinit var linearManager: LinearLayoutManager

    private val bakeryList = arrayListOf<DataVO.VoObject.Bakery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycler()
        requestApi()
    }

    fun setRecycler() {
        mainAdapter = mAdapter(bakeryList)
        linearManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewMainList.also {
            it.adapter = mainAdapter
            it.layoutManager = linearManager
        }
    }

    fun requestApi() {
        Thread {
            kotlin.run {
                domain = domain.replace(pattern, KEY)
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

                    test.voObject.bakeries.forEach {
                        if(it.storeState.contains("영업")){
                            bakeryList.add(it)
                            Log.e("😋", "${it.storeNm}, ${it.storeState}, ${it.storeTel}, ${it.storeAdr}")
                        }
                    }

                    //some tweaking
//                    val list = obj.getJSONObject("LOCALDATA_072218_GS").getJSONArray("row")
//                    val bakeryData = obj.get("LOCALDATA_072218_GS") as JSONObject
//                    val row = bakeryData.get("row") as JSONArray
//                    Log.e("test", "${obj.getJSONObject("LOCALDATA_072218_GS").getJSONArray("row")}")


                } catch (e: MalformedURLException) {
                    Log.e("exception", "$e")
                } catch (e: IOException) {
                    Log.e("exception", "$e")
                } catch (e: JSONException) { //json 데이터를 잘못 다뤘을 경우
                    Log.e("exception", "$e")
                } catch (e: NetworkOnMainThreadException) { //thread로 감싸주지 않았을 경우
                    Log.e("exception", "$e")
                }
            }
        }.start()

    }

}