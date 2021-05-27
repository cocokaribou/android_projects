package com.example.aos_framework_demo

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aos_framework_demo.databinding.ActivityExtensionBinding
import com.pionnet.overpass.extension.*

class ExtensionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExtensionBinding
    private lateinit var imgView: ImageView
    private val list = listOf(
        "https://postfiles.pstatic.net/MjAyMTA1MTRfMjg0/MDAxNjIwOTQxNjExNzUw.XqREuIKXmaaa4EuWZ_miZ1TjP8HvzqafMCatt4Ef5T0g.Tre7upXOfbn5CNaqRLKmolasay7hR0wCsnwaIMk5VpAg.JPEG.joyfuljuli/IMG_4531.jpg?type=w966",
        "https://postfiles.pstatic.net/MjAyMTA1MTRfMTcg/MDAxNjIwOTQxNjExNjg5.6N8dgOQnUSXbjFpJjbGTjfKL-FO-Be9hQFkAitJqJOsg.fgdYEa3rWtvsPm1dznr1NGGytPYMG8GFoffVeRr0PlIg.JPEG.joyfuljuli/IMG_4536.jpg?type=w966",
        "https://postfiles.pstatic.net/MjAyMTAxMTBfMjky/MDAxNjEwMjUxMDgzOTI0.sKYswEzUz2B7TQhbqNNw6TpQZofZNY5fX3YoFJLzgGcg.g8UluBPUjS8KoRMMVOotVUH0Va_dziA1WRD8IoJhrPcg.JPEG.joyfuljuli/IMG_3307.jpg?type=w966",
        "https://postfiles.pstatic.net/MjAyMTAxMTBfMTM3/MDAxNjEwMjUxMDgzODU5.L1m7Z775_2zgkAMhwVym1kbExb7nkEmZYrUEE4GjpNIg.GgWaGkTaBn6vbXNKQIOLYj_rauzUQ0PcKZgJZpITR2kg.JPEG.joyfuljuli/IMG_3308.jpg?type=w966",
        "https://postfiles.pstatic.net/MjAyMTAxMTBfMTE4/MDAxNjEwMjUxMDgzOTU0.Hr2dTjDfaO734HuRtqPKjhkWsqQJvAeZRVJIV2jg-wEg.Ge7iJ1UFfhp8p7pyitLNoFwWrp5CAGK_fw74MlqVuIYg.JPEG.joyfuljuli/IMG_3326.jpg?type=w966",
        "https://postfiles.pstatic.net/MjAyMTAxMTBfMTU4/MDAxNjEwMjUxMDg0MTQy.NjA-GbDTyIcgfeD0OtDtVUy8ZeGW-mmm6vT7CTRE0IEg.A2v8E-4a0_mDonXDgQej7mf-HzxVPdmY_BJk__hlY6wg.JPEG.joyfuljuli/IMG_3328.jpg?type=w966"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtensionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgView = binding.imgView

        /**
         * ImageView extensions(Glide)
         */
        binding.btnLoadImg.setOnClickListener {
            imgView.loadImage(setSplashImgURL(list, true))
        }

        binding.btnPreload.setOnClickListener {
            imgView.loadImagePreLoad(setSplashImgURL(list, true))
        }

        binding.btnWithScale.setOnClickListener {
            imgView.loadImageWithScale(setSplashImgURL(list, true), 100, 100)
        }

        binding.btnInCircle.setOnClickListener {
            imgView.loadImageCircle(setSplashImgURL(list, true), 500, 500)
        }


        /**
         * View.setLayoutWeight()
         */
        val editSum = binding.editLinearSum
        editSum.setOnKeyListener { k, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val sumStr = binding.editLinearSum.text.toString()
                try {
                    var sum = sumStr.toFloat()
                    if (sum > 1) {
                        editSum.setText("1")
                        sum = 1.0f
                    }
                    binding.editLinearSum.setLayoutWeight(sum)
                } catch (e: Exception) {
                    Log.e("exception", e.toString())
                }
            }
            false
        }


        /**
         * ConstraintLayout.setHorizontalBias()
         */
        val editBias = binding.editBias
        val outer = binding.constraintOuter
        editBias.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val biasStr = binding.editBias.text.toString()
                try {
                    var bias = biasStr.toFloat()
                    if (bias > 1) {
                        editBias.setText("1")
                        bias = 1.0f
                    }
                    outer.setHorizontalBias(editBias.id, bias)
                } catch (e: Exception) {
                    Log.e("exception", e.toString())
                }
            }
            false
        }

        /**
         * AnimationExtension
         */
        val txtAnimation = binding.txtAnimation
        val imgAnimation = binding.imgAnimation
        var switch = false

        txtAnimation.setUnderLine()
        txtAnimation.setOnClickListener {
            if (switch) {
                collapse(imgAnimation)
            } else {
                expand(imgAnimation)
            }
            switch = !switch
        }

    }

}
