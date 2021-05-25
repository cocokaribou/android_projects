package com.example.aos_framework_demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aos_framework_demo.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.pionnet.overpass.extension.*
import okhttp3.internal.Internal.instance
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toast: Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * gson 연습
         */
        val jsonTestString = getJsonFileToString("seoul.json", this)
        val gson = Gson() //gson : json 오브젝트를 java 오브젝트로 바꿔줌(data class)
        try {
            val response2 = gson.fromJson(jsonTestString, SeoulVO::class.java)
            response2.DATA.forEachIndexed { i, d ->
                Log.e("youngin", "자치구 코드 : ${d.cgg_code}, 자치구 이름: ${d.cgg_code_nm}")

                //nullable로 선언된 데이터의 null check
                if (d.upd_time != null) {
                    Log.e("youngin", d.upd_time.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("youngin", e.toString())
        }

        /**
         * getAppVersion(), isUpdate()
         */
        val btnCheckUpdate = binding.btnCheckUpdate
        btnCheckUpdate.setOnClickListener {
            val newVer = "2.5.0"
            val checker = isUpdate(getAppVersion(applicationContext), newVer)
            toast = when (checker) {
                true -> Toast.makeText(applicationContext, "업데이트가 필요합니다", Toast.LENGTH_SHORT)
                false -> Toast.makeText(applicationContext, "업데이트가 완료되었습니다", Toast.LENGTH_SHORT)
            }
            toast.show()
        }

        /**
         * setSplashImgURL()
         */
        val btnSetSplashImg = binding.btnSetSplashImg
        btnSetSplashImg.setOnClickListener {
            val arr = listOf(
                "https://img1.png",
                "https://img2.png",
                "https://img3.png",
                "https://img4.png",
                "https://img5.png"
            )
            val splashImg = setSplashImgURL(arr, true)
            toast = Toast.makeText(applicationContext, splashImg, Toast.LENGTH_SHORT)
            toast.show()
        }

        /**
         * getJsonFileToString()
         */
        binding.btnGetJson.setOnClickListener {
            val jsonString = getJsonFileToString("membership.json", this)
            toast = Toast.makeText(applicationContext, jsonString, Toast.LENGTH_SHORT)
            toast.show()
        }

        /**
         * toSimpleString()
         */
        binding.btnToSimpleStr.setOnClickListener {
            val date = Date()
            toast = Toast.makeText(applicationContext, date.toSimpleString(), Toast.LENGTH_SHORT)
            toast.show()
        }

        /**
         * getAddDateString()
         */
        val btnAddDate = binding.btnAddDate
        btnAddDate.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("일수를 입력하세요", false, btnAddDate.id)
        }

        /**
         * getCurrentTime()
         */
        binding.btnGetCurrTime.setOnClickListener {
            toast = Toast.makeText(applicationContext, getCurrentTime(), Toast.LENGTH_SHORT)
            toast.show()
        }

        /**
         * Int.dpToPx()
         */
        val btnDpToPx = binding.btnDpToPx
        btnDpToPx.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("dp 값을 입력하세요", false, btnDpToPx.id)
        }

        /**
         * TextView.setBoldText()
         */
        val btnSetBold = binding.btnSetBold
        btnSetBold.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("굵게 처리할 텍스트를 입력하세요", true, btnSetBold.id)
        }

        /**
         * TextView.setPriceStroke()
         */
        val btnSetStroke = binding.btnSetStroke
        btnSetStroke.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("취소선 처리할 텍스트를 입력하세요", true, btnSetStroke.id)
        }

        /**
         * priceFormat()
         */
        val btnPriceFormat = binding.btnPriceFormat
        btnPriceFormat.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("천 단위 수를 입력하세요", true, btnPriceFormat.id)
        }

        /**
         * productCnt()
         */
        val btnProductCnt = binding.btnProductCnt
        btnProductCnt.setOnClickListener {
            val dlg = CustomDialog(this)
            dlg.start("만 단위 수를 입력하세요", true, btnProductCnt.id)
        }

        val btnLayout = binding.btnLayout
        btnLayout.setOnClickListener {
            val intent = Intent(applicationContext, ExtensionActivity::class.java)
            startActivity(intent)
        }

        /**
         * sticky activity
         */
        binding.stickyBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, StickyActivity::class.java)
            startActivity(intent)
        }
    }

}
