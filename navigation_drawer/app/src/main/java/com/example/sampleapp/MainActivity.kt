package com.example.sampleapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import org.jsoup.select.NodeFilter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter

    private val linearManager = LinearLayoutManager(this)
    private val grid2Manager = GridLayoutManager(this, 2)
    private val grid4Manager = GridLayoutManager(this, 4)

    val dataList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            initData()
        }.start()

        initSliderListener()
        initAdapter()
    }

    fun initData() {
        val doc: Document =
            Jsoup.connect("https://www.zara.com/kr/ko/woman-new-in-l1180.html?v1=1881787").get()
        val selected = doc.body().select("#app-root").select(".theme").select("#theme-app")
            .select("div").select(".layout").select(".layout__content").select("main")
            .select("article").select(".product-groups").select(".product-grid")
            .select(".product-grid__product-list")

        // filter
        selected.filter(object:NodeFilter{
            override fun head(node: Node, depth: Int): NodeFilter.FilterResult {
                TODO("Not yet implemented")
            }

            override fun tail(node: Node, depth: Int): NodeFilter.FilterResult {
                TODO("Not yet implemented")
            }

        })

        Logger.e("doc title $selected")
    }

    fun initSliderListener() {

        val touchListener = object : Slider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: Slider) {
            }

            // notify data change
            override fun onStopTrackingTouch(slider: Slider) {
                if (slider.value < 0.35) {
                    binding.slider.value = 0.1f
                    binding.recyclerview.layoutManager = linearManager
                    mainAdapter.setItemViewType(MainAdapter.VIEWTYPE_BIGHOLDER)
                } else if (slider.value in 0.35..0.55) {
                    binding.slider.value = 0.5f
                    binding.recyclerview.layoutManager = grid2Manager
                    mainAdapter.setItemViewType(MainAdapter.VIEWTYPE_GRID2HOLDER)
                } else {
                    binding.slider.value = 0.9f
                    binding.recyclerview.layoutManager = grid4Manager
                    mainAdapter.setItemViewType(MainAdapter.VIEWTYPE_GRID4HOLDER)
                }
            }

        }

        binding.slider.addOnSliderTouchListener(touchListener)
    }

    fun initAdapter() {
        mainAdapter = MainAdapter()

        mainAdapter.submitList(dataList)

        binding.recyclerview.adapter = mainAdapter
        binding.recyclerview.layoutManager = linearManager

    }
}