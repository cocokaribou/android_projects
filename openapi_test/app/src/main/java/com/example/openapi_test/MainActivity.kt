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

    private val KEY: String = BuildConfig.API_KEY //com.google.secrets_gradle_plugin
    private var domain: CharSequence =
        "http://openapi.seoul.go.kr:8088/(key)/json/LOCALDATA_072218_GS/(page)/600/"
    private val keyPattern: Regex = "\\([key]*\\)".toRegex() //인증키 정규식
    private var pagePattern: Regex = "\\([page]*\\)".toRegex() // 페이지 정규식
    //스크롤 마지막 단이 닿았을 때 page+1

    lateinit var binding: ActivityMainBinding
    lateinit var mainAdapter: mAdapter
    lateinit var linearManager: LinearLayoutManager

    var bakeryList = arrayListOf<DataVO.VoObject.Bakery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         //api에서 정보 가져오기
        setRecycler(requestApi()) //리사이클러 초기화

        Log.e("와 이래도 안된다고", "${requestApi().size}")
    }


    fun requestApi(): ArrayList<DataVO.VoObject.Bakery> {
        val resultArr = arrayListOf<DataVO.VoObject.Bakery>()
        Thread {
            kotlin.run {
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


                    //여기서 페이징과 어댑터 연결을 다 해줘야할 것 같은.. 그런 슬픈 느낌...

                    test.voObject.bakeries.forEach {
                        if (it.storeState.contains("영업")) {

                            resultArr.add(it)
                            Log.e("😋", "${it.storeNm}, ${it.storeState}, ${it.storeTel}, ${it.storeAdr}")
                        }
                    }
                    Log.e("mainActivity", "listSize: ${bakeryList.size}")

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
        }.start() //Runnable로 안 감싸도 되나?
        Log.e("이쯤되면 내가 스레드를 모르는건가 싶은데", "${resultArr.size}")
        return resultArr
    }

    fun setRecycler(list:ArrayList<DataVO.VoObject.Bakery>) {
        Log.e("넘겨주기", "넘겨주기: ${list.size}")
        mainAdapter = mAdapter(this.bakeryList)
        linearManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewMainList.also {
            it.adapter = mainAdapter
            it.layoutManager = linearManager
        }
    }

}
