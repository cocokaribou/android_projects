package com.example.aos_framework_demo

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.aos_framework_demo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.pionnet.overpass.extension.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonTestString = "seoul.json"
        Log.e("88", getJsonFileToString(jsonTestString, this))

        val jsonTestString2 = "서울시 지정·인증업소 현황.json"
        Log.e("**", getJsonFileToString(jsonTestString2, this))


        val jsonString = "membership.json" //new jaju 참고
        var resultStr = getJsonFileToString(jsonString, this)


        val gson = Gson() //gson: json을 보기좋게 해주는 라이브러리
        try {
            val response = gson.fromJson(resultStr, MemberShip::class.java)
            Log.e("test", response.bannerInfo.bannerImgPath) //setText로 화면에 띄우기

        } catch (e: Exception) {

        }

    }

}