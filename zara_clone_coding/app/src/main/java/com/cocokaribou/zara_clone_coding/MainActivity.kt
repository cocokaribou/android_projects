package com.cocokaribou.zara_clone_coding

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cocokaribou.zara_clone_coding.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Thread {
            testFun()
        }.start()
    }

    fun testFun() {
        val doc: Document =
            Jsoup.connect("https://www.zara.com/kr/ko/man-trend-1-l835.html?v1=1886232").get()
        val title = doc.title()
        Log.e("document title", "$title")
    }
}