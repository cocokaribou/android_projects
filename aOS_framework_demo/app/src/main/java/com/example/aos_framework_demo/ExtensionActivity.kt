package com.example.aos_framework_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aos_framework_demo.databinding.ActivityExtensionBinding
import com.pionnet.overpass.extension.*

class ExtensionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExtensionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtensionBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val img1 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMjg0/MDAxNjIwOTQxNjExNzUw.XqREuIKXmaaa4EuWZ_miZ1TjP8HvzqafMCatt4Ef5T0g.Tre7upXOfbn5CNaqRLKmolasay7hR0wCsnwaIMk5VpAg.JPEG.joyfuljuli/IMG_4531.jpg?type=w966"
            val img2 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMTcg/MDAxNjIwOTQxNjExNjg5.6N8dgOQnUSXbjFpJjbGTjfKL-FO-Be9hQFkAitJqJOsg.fgdYEa3rWtvsPm1dznr1NGGytPYMG8GFoffVeRr0PlIg.JPEG.joyfuljuli/IMG_4536.jpg?type=w966"
            val img3 =
                "https://postfiles.pstatic.net/MjAyMTA1MTFfMTYx/MDAxNjIwNjg5NzA2ODc1.vMjuJrBaRN7cWolpOmh6PGBNRwnpGwoOEfwjYx_4zCgg.plhRKs7O-8oJYrIWCtILe62X115OLxyQJqUuPuAcxUcg.JPEG.joyfuljuli/B0EA9AB3-54C2-429B-821E-7CA3EE8FD5FC.jpeg?type=w966"
            val img4 =
                "https://postfiles.pstatic.net/MjAyMTA1MDFfMjEy/MDAxNjE5ODQ0Njg1Njkx.KzTs9nM4Thik7n225tDp8zDMwL98Et0V-iLgar2gAlAg.GBAJmDWra1r3sWVWC0qljrT0rP7ozhLmSxZCS_uVqqAg.JPEG.joyfuljuli/w0v8ozgabyi61.jpg?type=w966"
            val arr = arrayOf(img1, img2, img3, img4)

            val imgView: ImageView = findViewById(R.id.imgView)

//            arr.forEach {
//                imgView.loadImageWithOriginal(it)
//            }
//            imgView.loadImageCircle(setSplashImgURL(arr, true))

            preLoadSplash(this@ExtensionActivity, setSplashImgURL(arr, true))
//            imgView.loadImageWithScale(setSplashImgURL(arr, true), 500, 500)
//            val splashImg = setSplashImgURL(arr, true)
//            Glide.with(this@ExtensionActivity)
//                .load(splashImg)
//                .into(imgView)
        }
    }
}