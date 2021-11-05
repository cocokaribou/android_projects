package com.cocokaribou.thread_1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.thread_1.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runBlocking {
            // 메인 스레드를 블로킹하는 스코프
            // ui 그리는데 메인 블로킹하면 안 될 것 같은데, 되네

//            binding.tv1.text =
//                "start\nthread=[${Thread.currentThread().name}]\ncoroutineContext=$coroutineContext"
            // runBlocking 안에서 바로 실행하면 BlockingCoroutine

            launch {
                delay(1000)
                binding.tv1.text =
                    "start\nthread=[${Thread.currentThread().name}]\ncoroutineContext=$coroutineContext"
            }

            launch {
                delay(3000)
                runOnUiThread {
                    binding.tv2.text =
                        "1000 delayed coroutine\nthread=[${Thread.currentThread().name}]\ncoroutineContext=$coroutineContext"
                }
            }

            GlobalScope.launch {
                delay(6000)
                runOnUiThread {
                    binding.tv3.text =
                        "3000 delayed coroutine\nthread=[${Thread.currentThread().name}]\ncoroutineContext=$coroutineContext"
                }
            }
        }


        // BlockingEventLoop vs Dispatchers.Default ??
    }
}