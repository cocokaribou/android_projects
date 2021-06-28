package com.cocokaribou.thread_1

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.thread_1.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn1.setOnClickListener {
            Thread {
                run {
                    for (i in binding.seekbar1.progress until 100 step 2) {
                        runOnUiThread {
                            run {
                                binding.seekbar1.progress = binding.seekbar1.progress + 2
                                binding.tv1.text =
                                    "진행율: ${binding.seekbar1.progress}%"
                            }
                        }
                        SystemClock.sleep(100)
                    }
                    Log.e("current Thread", Thread.currentThread().name)
                }
            }.start()
            Log.e("current Thread", Thread.currentThread().name)
            Thread {
                run {
                    for (i in binding.seekbar2.progress until 100) {
                        runOnUiThread {
                            run {
                                binding.seekbar2.progress = binding.seekbar2.progress + 1
                                binding.tv2.text =
                                    "진행율: ${binding.seekbar2.progress}%"
                            }
                        }
                        SystemClock.sleep(200)
                    }
                    Log.e("current Thread", Thread.currentThread().name)

                }
            }.start()
            Log.e("current Thread", Thread.currentThread().name)
        }
    }
}