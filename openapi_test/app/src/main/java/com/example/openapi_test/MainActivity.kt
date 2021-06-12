package com.example.openapi_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private val KEY: String = "demo" //TODO 하드코딩된 api key
    private var domain: CharSequence =
        "http://openapi.seoul.go.kr:8088/(인증키)/json/LOCALDATA_072218_GS/1/5/"
    private val pattern: Regex = "\\([^)]*\\)".toRegex() //괄호안 정규식

    lateinit var binding: ActivityMainBinding
    lateinit var mainRecycler: RecyclerView
    lateinit var mainAdapter: mAdapter
    lateinit var linearManager: LinearLayoutManager

    private val list = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycler()
        requestApi()
    }

    fun setRecycler() {
        mainAdapter = mAdapter(list)
        linearManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewMainList.also {
            it.adapter = mainAdapter
            it.layoutManager = linearManager
        }
    }

    fun requestApi() {
        domain = domain.replace(pattern, KEY)
        try {
            val url = URL(domain as String)

            val stream: InputStream = url.openStream()
            val streamReader = InputStreamReader(stream)
            val reader = BufferedReader(streamReader)

            val buffer = StringBuffer()
            var line = reader.readLine()

            while(!line.isNullOrBlank()){
                buffer.append(line + "\n")
                line = reader.readLine()
            }

            val jsonData = buffer.toString()

            val obj = JSONObject(jsonData)
            Log.e("data", "${obj}")

            val bakeryData = obj.get("LOCALDATA_072218_GS") as JSONObject



        } catch (e: MalformedURLException) {
            Log.e("exception", "$e")
        } catch (e: IOException) {
            Log.e("exception", "$e")
        } catch (e: JSONException) {
            Log.e("exception", "$e")
//        } catch (e: NetworkOnMainThreadException){
//            Log.e("왜지..?", "왜지감자?")
        }
    }

}