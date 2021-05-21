package com.example.aos_framework_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
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

        /**
         * gson 연습
         */
        val jsonTestString = getJsonFileToString("seoul.json", this)

        val gson = Gson() //gson : json 오브젝트를 java 오브젝트로 바꿔줌(data class)
        try {
            val response2 = gson.fromJson(jsonTestString, SeoulVO::class.java)
            Log.e("youngin", response2.DESCRIPTION.UPSO_NM)
            Log.e("youngin", response2.DATA[0].cgg_code)

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
            val txt_group = binding.txtGroupCheckUpdate
            txt_group.visibility = if (txt_group.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            val txtCurrVerInt = binding.txtCurrVerInt
            txtCurrVerInt.setText(getAppVersion(applicationContext))

            val txtCheckResult = binding.txtCheckResult
            val newVer = "2.5.0"
            val checker = isUpdate(getAppVersion(applicationContext), newVer)
            when (checker) {
                true -> txtCheckResult.setText("업데이트가 필요합니다")
                false -> txtCheckResult.setText("업데이트 완료됐습니다")
            }
        }

        /**
         * setSplashImgURL()
         */
        val btnSetSplashImg = binding.btnSetSplashImg
        val txtSplashImgResult = binding.txtSplashImgResult
        btnSetSplashImg.setOnClickListener {
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
            txtSplashImgResult.setText(splashImg)

//            val toast = Toast.makeText(applicationContext, splashImg, Toast.LENGTH_SHORT)
//            toast.show()

//            Glide.with(this@ExtensionActivity)
//                .load(splashImg)
//                .override(500, 500)
//                .into(imgView)
        }

        /**
         * getJsonFileToString()
         */
        binding.btnGetJson.setOnClickListener {
            val jsonString = getJsonFileToString("membership.json", this)
            val toast = Toast.makeText(applicationContext, jsonString, Toast.LENGTH_SHORT)
            toast.show()
        }

        /**
         * toSimpleString()
         */
        binding.btnToSimpleStr.setOnClickListener {
            val date = Date()
            binding.txtSimpleStrResult.setText(date.toSimpleString())
        }

        /**
         * getAddDateString()
         */
        val btnAddDate = binding.btnAddDate
        val editTxtAddDate = binding.editTxtAddDate
        val txtAddDateResult = binding.txtAddDateResult
        btnAddDate.setOnClickListener {
            editTxtAddDate.visibility = if (editTxtAddDate.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            txtAddDateResult.visibility = if (txtAddDateResult.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            editTxtAddDate.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KEYCODE_ENTER) {
                    val dates = editTxtAddDate.getText().toString()
                    val addDate = getAddDateString("dd/MM/yyyy", Integer.parseInt(dates))
                    txtAddDateResult.setText("${dates}일 후 ${addDate}")
                    txtAddDateResult.visibility = View.VISIBLE
                    editTxtAddDate.visibility = View.INVISIBLE
                    true
                } else false
            }
        }


        /**
         * getCurrentTime()
         */
        binding.btnGetCurrTime.setOnClickListener{
            binding.txtGetCurrTime.setText(getCurrentTime())
        }


        /**
         * Int.dpToPx
         */
        val btnDpToPx = binding.btnDpToPx
        val editTxtDpToPx = binding.editTxtDpToPx
        val txtDpToPx = binding.txtDpToPx
        btnDpToPx.setOnClickListener {
            editTxtDpToPx.visibility = if (editTxtDpToPx.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            txtDpToPx.visibility = if (txtDpToPx.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }

            editTxtDpToPx.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KEYCODE_ENTER) {
                    val dp = Integer.parseInt(editTxtDpToPx.getText().toString())
                    val pixel = dp.dpToPx(this)
                    txtDpToPx.setText("${dp} dp -> ${pixel} pixel")
                    txtDpToPx.visibility = View.VISIBLE
                    editTxtDpToPx.visibility = View.INVISIBLE
                    true
                } else false
            }
        }


        /**
         * TextView.setBoldText()
         */
        val btnSetBold = binding.btnSetBold
        val editTxtSetBold = binding.editTxtSetBold
        val txtBeforeBold = binding.txtBeforeBold
        val txtSetBold = binding.txtSetBold
        btnSetBold.setOnClickListener {
            editTxtSetBold.visibility = if (editTxtSetBold.visibility == View.INVISIBLE) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            if (txtSetBold.visibility == View.INVISIBLE) {
                txtBeforeBold.visibility = View.VISIBLE
                txtSetBold.visibility = View.VISIBLE
            } else {
                txtBeforeBold.visibility = View.INVISIBLE
                txtSetBold.visibility = View.INVISIBLE
            }

            editTxtSetBold.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KEYCODE_ENTER) {
                    val txt = editTxtSetBold.getText().toString()
                    txtBeforeBold.setText("${txt} -> ")
                    txtSetBold.setText(txt)
                    txtSetBold.setBoldText()

                    txtBeforeBold.visibility = View.VISIBLE
                    txtSetBold.visibility = View.VISIBLE
                    editTxtSetBold.visibility = View.INVISIBLE
                    true
                } else false
            }
        }

        /**
         * TextView.setPriceStroke()
         */
        val btnSetStroke = binding.btnSetStroke
        val editTxtSetStroke = binding.editTxtSetStroke
        val txtSetStroke = binding.txtSetStroke
        btnSetStroke.setOnClickListener{
            editTxtSetStroke.visibility = if(editTxtSetStroke.visibility == View.INVISIBLE){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
            txtSetStroke.visibility = if(txtSetStroke.visibility == View.INVISIBLE){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
        }
        editTxtSetStroke.setOnKeyListener{ v, keyCode, event ->
            if(keyCode == KEYCODE_ENTER){
                val txt = editTxtSetStroke.getText().toString()
                txtSetStroke.setText(txt)
                txtSetStroke.setPriceStroke()

                txtSetStroke.visibility = View.VISIBLE
                editTxtSetStroke.visibility = View.INVISIBLE
                true
            }else false
        }


        /**
         * priceFormat()
         */
        val btnPriceFormat = binding.btnPriceFormat
        val editTxtPriceFormat = binding.editTxtPriceFormat
        val txtPriceFormat = binding.txtPriceFormat
        btnPriceFormat.setOnClickListener{
            if(editTxtPriceFormat.visibility == View.INVISIBLE){
                editTxtPriceFormat.visibility = View.VISIBLE
                editTxtPriceFormat.requestFocus()
            }else{
                editTxtPriceFormat.visibility = View.INVISIBLE
            }
            txtPriceFormat.visibility = if(txtPriceFormat.visibility == View.INVISIBLE){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
        }
        editTxtPriceFormat.setOnKeyListener{ v, keyCode, event ->
            if(keyCode == KEYCODE_ENTER){
                val digit = editTxtPriceFormat.getText().toString()
                txtPriceFormat.setText(priceFormat(digit))

                txtPriceFormat.visibility = View.VISIBLE
                editTxtPriceFormat.visibility = View.INVISIBLE
                true
            }else false
        }

        /**
         * productCnt()
         */
        val btnProductCnt = binding.btnProductCnt
        val editTxtProductCnt = binding.editTxtProductCnt
        val txtProductCnt = binding.txtProductCnt
        btnProductCnt.setOnClickListener{
            if(editTxtProductCnt.visibility == View.INVISIBLE){
                editTxtProductCnt.visibility = View.VISIBLE
                editTxtProductCnt.requestFocus()
            }else{
                editTxtProductCnt.visibility = View.INVISIBLE
            }
            txtProductCnt.visibility = if(txtProductCnt.visibility == View.INVISIBLE){
                View.VISIBLE
            }else{
                View.INVISIBLE
            }
        }
        editTxtProductCnt.setOnKeyListener{ v, keyCode, event ->
            if(keyCode == KEYCODE_ENTER){
                val digit = editTxtProductCnt.getText().toString().toLong()
                txtProductCnt.setText(productCnt(digit))

                txtProductCnt.visibility = View.VISIBLE
                editTxtProductCnt.visibility = View.INVISIBLE
                true
            }else false
        }

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




