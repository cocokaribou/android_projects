package com.example.aos_framework_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.aos_framework_demo.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.demo1Extension.setOnClickListener {
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.demo2Extension.setOnClickListener {
            val intent = Intent(this@IntroActivity, ExtensionActivity::class.java)
            startActivity(intent)
        }
        binding.demo1CustomView.setOnClickListener {
            val intent = Intent(this@IntroActivity, RecyclerActivity::class.java)
            startActivity(intent)
        }
        binding.demo2CustomView.setOnClickListener {
            val intent = Intent(this@IntroActivity, StickyActivity::class.java)
            startActivity(intent)
        }
        binding.demo3CustomView.setOnClickListener {
            val intent = Intent(this@IntroActivity, ElandActivity::class.java)
            startActivity(intent)
        }
    }
}