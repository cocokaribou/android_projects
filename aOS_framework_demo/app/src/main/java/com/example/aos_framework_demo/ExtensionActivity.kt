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

        binding.btnLoadImg.setOnClickListener(ButtonListener())

    }

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val img1 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMjg0/MDAxNjIwOTQxNjExNzUw.XqREuIKXmaaa4EuWZ_miZ1TjP8HvzqafMCatt4Ef5T0g.Tre7upXOfbn5CNaqRLKmolasay7hR0wCsnwaIMk5VpAg.JPEG.joyfuljuli/IMG_4531.jpg?type=w966"
            val img2 =
                "https://postfiles.pstatic.net/MjAyMTA1MTRfMTcg/MDAxNjIwOTQxNjExNjg5.6N8dgOQnUSXbjFpJjbGTjfKL-FO-Be9hQFkAitJqJOsg.fgdYEa3rWtvsPm1dznr1NGGytPYMG8GFoffVeRr0PlIg.JPEG.joyfuljuli/IMG_4536.jpg?type=w966"
            val img3 =
                "https://postfiles.pstatic.net/MjAyMTAxMTBfMjky/MDAxNjEwMjUxMDgzOTI0.sKYswEzUz2B7TQhbqNNw6TpQZofZNY5fX3YoFJLzgGcg.g8UluBPUjS8KoRMMVOotVUH0Va_dziA1WRD8IoJhrPcg.JPEG.joyfuljuli/IMG_3307.jpg?type=w966"
            val img4 =
                "https://postfiles.pstatic.net/MjAyMTAxMTBfMTM3/MDAxNjEwMjUxMDgzODU5.L1m7Z775_2zgkAMhwVym1kbExb7nkEmZYrUEE4GjpNIg.GgWaGkTaBn6vbXNKQIOLYj_rauzUQ0PcKZgJZpITR2kg.JPEG.joyfuljuli/IMG_3308.jpg?type=w966"
            val img5 =
                "https://postfiles.pstatic.net/MjAyMTAxMTBfMTE4/MDAxNjEwMjUxMDgzOTU0.Hr2dTjDfaO734HuRtqPKjhkWsqQJvAeZRVJIV2jg-wEg.Ge7iJ1UFfhp8p7pyitLNoFwWrp5CAGK_fw74MlqVuIYg.JPEG.joyfuljuli/IMG_3326.jpg?type=w966"
            val img6 =
                "https://postfiles.pstatic.net/MjAyMTAxMTBfMTU4/MDAxNjEwMjUxMDg0MTQy.NjA-GbDTyIcgfeD0OtDtVUy8ZeGW-mmm6vT7CTRE0IEg.A2v8E-4a0_mDonXDgQej7mf-HzxVPdmY_BJk__hlY6wg.JPEG.joyfuljuli/IMG_3328.jpg?type=w966"
            val list = listOf(img1, img2, img3, img4, img5, img6)

            val imgView: ImageView = findViewById(R.id.imgView)

//            arr.forEach {
//                imgView.loadImageWithOriginal(it)
//            }
            imgView.loadImageWithScale(setSplashImgURL(list, true), 500, 500)

//            preLoadSplash(this@ExtensionActivity, setSplashImgURL(list, true))
//            imgView.loadImageWithScale(setSplashImgURL(arr, true), 500, 500)
//            val splashImg = setSplashImgURL(arr, true)
//            Glide.with(this@ExtensionActivity)
//                .load(splashImg)
//                .into(imgView)
        }
    }
}