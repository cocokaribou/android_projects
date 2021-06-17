package com.example.openapi_test

import android.icu.lang.UCharacter
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
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var mainAdapter: mAdapter
    lateinit var linearManager: LinearLayoutManager

        val bakeryList = Vector<DataVO.VoObject.Bakery>()
    //    val bakeryList = arrayListOf<DataVO.VoObject.Bakery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestApi(bakeryList).start()//api에서 정보 가져오기

        binding.recyclerViewMainList.apply{
            adapter=mAdapter(bakeryList)
            layoutManager=LinearLayoutManager(this@MainActivity)
        }
    }

    class requestApi(private val bakeryList:Vector<DataVO.VoObject.Bakery>) : Thread() {
        private val KEY: String = BuildConfig.API_KEY //com.google.secrets_gradle_plugin
        private var domain: CharSequence =
            "http://openapi.seoul.go.kr:8088/(key)/json/LOCALDATA_072218_GS/(page)/600/"
        private val keyPattern: Regex = "\\([key]*\\)".toRegex() //인증키 정규식
        private var pagePattern: Regex = "\\([page]*\\)".toRegex() // 페이지 정규식

        //스크롤 마지막 단이 닿았을 때 page+1
        override fun
                run() {
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
                test.voObject.bakeries.forEach {
                    if (it.storeState.contains("영업") && (!it.storeNm.contains("파리바게뜨") && (!it.storeNm.contains(
                            "뚜레쥬르"
                        )))
                    ) {
                        bakeryList.add(it)
                        Log.e(
                            "😋",
                            "${it.storeNm}, ${it.storeState}, ${it.storeTel}, ${it.storeAdr}"
                        )
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
            } catch (e: NetworkOnMainThreadException) { //thread로 감싸주지 않았을 경우, 메인스레드에서 데이터통신 할 경우
                Log.e("exception", "$e")
            }
        }
    }
}


