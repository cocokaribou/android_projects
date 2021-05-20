package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.example.aos_framework_demo.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.gson.Gson
import com.pionnet.overpass.customView.Dialog
import com.pionnet.overpass.extension.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonTestString = getJsonFileToString("seoul.json", this)
        var resultStr = getJsonFileToString("membership.json", this)

        val gson = Gson() //gson : json 오브젝트를 java 오브젝트로 바꿔줌(data model)
        try {
            val response = gson.fromJson(resultStr, MemberShip::class.java)
            Log.e("test", response.bannerInfo.bannerImgPath) //화면에 setText로
            val response2 = gson.fromJson(jsonTestString, SeoulVO::class.java)
            Log.e("test2", response2.DESCRIPTION.UPSO_NM)
            Log.e("test3", response2.DATA[0].cgg_code)

            response2.DATA.forEachIndexed { i, d ->
                Log.e("tag $i", "자치구 코드 : ${d.cgg_code}, 자치구 이름: ${d.cgg_code_nm}")

                //nullable로 선언된 데이터의 null check
                if (d.upd_time != null) {
                    Log.e("^^", d.upd_time.toString())
                }
            }

        } catch (e: Exception) {
            Log.e("tag", e.toString())
        }
        binding.btnCheckUpdate.setOnClickListener {
            val txt_group = binding.txtGroupCheckUpdate
            txt_group.visibility = if (txt_group.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            val txt_currVer_int: TextView = findViewById(R.id.txt_currVer_int)
            txt_currVer_int.setText(getAppVersion(applicationContext))

            val txt_checkResult: TextView = findViewById(R.id.txt_checkResult)
            val newVer = "2.5.0"
            val checker = isUpdate(getAppVersion(applicationContext), newVer)
            when (checker) {
                true -> txt_checkResult.setText("업데이트가 필요합니다")
                false -> txt_checkResult.setText("업데이트 완료됐습니다")
            }
        }

        binding.btnSetSplashImg.setOnClickListener {
//            val img1 =
//                "https://postfiles.pstatic.net/MjAyMTA1MTRfMjg0/MDAxNjIwOTQxNjExNzUw.XqREuIKXmaaa4EuWZ_miZ1TjP8HvzqafMCatt4Ef5T0g.Tre7upXOfbn5CNaqRLKmolasay7hR0wCsnwaIMk5VpAg.JPEG.joyfuljuli/IMG_4531.jpg?type=w966"
//            val img2 =
//                "https://postfiles.pstatic.net/MjAyMTA1MTRfMTcg/MDAxNjIwOTQxNjExNjg5.6N8dgOQnUSXbjFpJjbGTjfKL-FO-Be9hQFkAitJqJOsg.fgdYEa3rWtvsPm1dznr1NGGytPYMG8GFoffVeRr0PlIg.JPEG.joyfuljuli/IMG_4536.jpg?type=w966"
//            val arr = arrayOf(img1, img2)

            val arr = arrayOf(
                "https://img1.png",
                "https://img2.png",
                "https://img3.png",
                "https://img4.png",
                "https://img5.png"
            )
            val splashImg = setSplashImgURL(arr, true)
            binding.txtSplashImgResult.setText(splashImg)

//            val toast = Toast.makeText(applicationContext, splashImg, Toast.LENGTH_SHORT)
//            toast.show()

//            Glide.with(this@ExtensionActivity)
//                .load(splashImg)
//                .override(500, 500)
//                .into(imgView)
        }

        binding.btnGetJson.setOnClickListener {
            val jsonString = getJsonFileToString("membership.json", this)
            val toast = Toast.makeText(applicationContext, jsonString, Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.btnToSimpleStr.setOnClickListener {
            val date = Date()
            binding.txtSimpleStrResult.setText(date.toSimpleString())
        }


        binding.btnAddDate.setOnClickListener {
            binding.txtGroupAddDate.visibility = View.VISIBLE

            val dates = Integer.parseInt(binding.editTxtAddDate.getText().toString())

            if (dates.toString().length == 0) {
                val toast = Toast.makeText(applicationContext, "일수를 입력하세요", Toast.LENGTH_SHORT)
                toast.show()
            } else {}
        }


    val dates: Int = 10
    Log.e("test", "현재 날짜: ${getCurrentTime()}")
    Log.e("test", "현재 날짜로부터 ${dates}일 뒤는 ${getAddDateString("dd/MM/yyyy", dates)} 입니다")
    }


}


//    inner class Button3Listener : View.OnClickListener {
//        override fun onClick(v: View?) {
//            val dialogBuilder = Dialog.DialogBuilder()
//            dialogBuilder.setTitle("test")
//            dialogBuilder.setDescription("this is a test")
//            dialogBuilder.setNegativeBtnText("nope")
//            dialogBuilder.setPositiveBtnText("yup")
//            val dialog = dialogBuilder.create()
//            dialog.show(FragmentManager, dialog.tag)
//        }
//    }




