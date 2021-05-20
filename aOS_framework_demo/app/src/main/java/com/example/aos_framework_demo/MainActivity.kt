package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.aos_framework_demo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.pionnet.overpass.extension.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonTestString = getJsonFileToString("seoul.json", this)
        var resultStr = getJsonFileToString("membership.json", this)

        val gson = Gson() //gson : json 오브젝트를 java 오브젝트로 바꿔줌(data model)
        try {
            val response = gson.fromJson(resultStr, MemberShip::class.java)
            Log.e("test", response.bannerInfo.bannerImgPath) //화면에 setText로
            val response2 = gson.fromJson(jsonTestString, SeoulVO::class.java)
            Log.e("test2", response2.DESCRIPTION.UPSO_NM)
            Log.e("test3", response2.DATA[0].cgg_code)

            response2.DATA.forEachIndexed{ i, d ->
                Log.e("tag $i", "자치구 코드 : ${d.cgg_code}, 자치구 이름: ${d.cgg_code_nm}", )
            }


        } catch (e: Exception) {
            Log.e("tag", e.toString())
        }

    }

}