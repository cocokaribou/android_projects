package com.cocokaribou.thread_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.thread_1.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButton()
    }

    private fun initButton() {
        binding.btn1.setOnClickListener(btnListener)
        binding.btn2.setOnClickListener(btnListener)
        binding.btn3.setOnClickListener(btnListener)
        binding.btn4.setOnClickListener(btnListener)
    }

    private val btnListener = View.OnClickListener { view ->
        val intent: Intent =
            when (view.id) {
                binding.btn1.id -> {
                    Intent(this, MainActivity::class.java)
                }
                binding.btn2.id -> {
                    Intent(this, MainActivity2::class.java)
                }
                binding.btn3.id -> {
                    Log.e("youngin", "clicked")
                    Intent(this, MainActivity3::class.java)
                }
                binding.btn4.id -> {
                    Log.e("youngin", "clicked")
                    Intent(this, CoroutineActivity::class.java)
                }
                else -> {
                    Intent(this, CoroutineActivity::class.java)
                }
            }
        startActivity(intent)
    }

    companion object {
        lateinit var binding :ActivityIntroBinding

    }
}