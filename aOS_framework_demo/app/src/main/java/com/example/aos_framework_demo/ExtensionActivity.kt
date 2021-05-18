package com.example.aos_framework_demo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.pionnet.overpass.extension.getAppVersion
import com.pionnet.overpass.extension.isUpdate
import com.pionnet.overpass.extension.setSplashImgURL

class ExtensionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_extension)

        val button : Button = findViewById(R.id.btn_setSplashImg)
        button.setOnClickListener(ButtonListener())
    }

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val img1 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMjg0/MDAxNjIwOTQxNjExNzUw.XqREuIKXmaaa4EuWZ_miZ1TjP8HvzqafMCatt4Ef5T0g.Tre7upXOfbn5CNaqRLKmolasay7hR0wCsnwaIMk5VpAg.JPEG.joyfuljuli/IMG_4531.jpg?type=w966"
            val img2 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMTcg/MDAxNjIwOTQxNjExNjg5.6N8dgOQnUSXbjFpJjbGTjfKL-FO-Be9hQFkAitJqJOsg.fgdYEa3rWtvsPm1dznr1NGGytPYMG8GFoffVeRr0PlIg.JPEG.joyfuljuli/IMG_4536.jpg?type=w966"
            val arr = arrayOf(img1, img2)

            val imgView: ImageView = findViewById(R.id.imgView)

            val splashImg = setSplashImgURL(arr, true)
            Glide.with(this@ExtensionActivity)
                .load(splashImg)
                .override(500, 500)
                .into(imgView)
        }
    }

    fun onIsUpdate(view: View) {
        val checker = isUpdate(getAppVersion(applicationContext), "2.5.0")
        var resultStr = ""
        resultStr = when (checker) {
            true -> "업데이트가 필요합니다"
            false -> "up to date"
        }
        val toast = Toast.makeText(applicationContext, resultStr, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun onGetAppVersion(view: View) {
        var appVerStr = getAppVersion(applicationContext)
        val toast = Toast.makeText(applicationContext, appVerStr, Toast.LENGTH_SHORT)
        toast.show()
    }
}