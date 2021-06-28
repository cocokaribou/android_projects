package com.cocokaribou.thread_1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.thread_1.databinding.ActivityCoroutineBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv1.text = "Start ${Thread.currentThread().name}"


        //
        GlobalScope.launch {
            delay(3000)
//            binding.tv2.text = "3000 delayed coroutine"
            Log.e("$tag", "3000 delayed coroutine")
        }
    }
}