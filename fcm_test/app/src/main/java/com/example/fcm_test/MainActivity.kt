package com.example.fcm_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fcm_test.databinding.ActivityMainBinding
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrash.setOnClickListener {
            throw RuntimeException("test crash!")
        }
    }
}