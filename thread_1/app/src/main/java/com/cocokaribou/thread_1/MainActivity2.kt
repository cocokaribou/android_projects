package com.cocokaribou.thread_1

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.thread_1.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn1.setOnClickListener {
            Thread {
                run() {
                    var index = binding.seekbar1.progress
                    for (i in index until 100) {
                        binding.seekbar1.setProgress(binding.seekbar1.progress + 2)
                        SystemClock.sleep(100)
                    }
                    Log.e("current Thread", Thread.currentThread().name)
                }
            }.start()
            Log.e("current Thread", Thread.currentThread().name)

            Thread {
                run() {
                    var index2 = binding.seekbar2.progress
                    for (i in index2 until 100) {
                        binding.seekbar2.setProgress(binding.seekbar2.progress + 1)
                        SystemClock.sleep(100)
                    }
                    Log.e("current Thread", Thread.currentThread().name)
                }
            }.start()
            Log.e("current Thread", Thread.currentThread().name)
        }
    }
}