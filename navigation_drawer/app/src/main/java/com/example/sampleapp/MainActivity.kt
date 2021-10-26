package com.example.sampleapp

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

    val imgUrlList = mutableListOf<String>()

    private val linearManager = LinearLayoutManager(this)
    private val grid2Manager = GridLayoutManager(this, 2)
    private val grid4Manager = GridLayoutManager(this, 4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        Thread {
            initData()
        }.start()

        initSliderListener()
    }

    private fun initData() {
        val doc: Document =
            Jsoup.connect("https://store.musinsa.com/app/styles/lists").get()

        // filter
        doc.body().filter(object : NodeFilter {
            override fun head(node: Node, depth: Int): NodeFilter.FilterResult {
                val mChildNode = node.childNodes()
                for (i in mChildNode) {
                    if (i.attr("class") == "style-list-thumbnail") {
                        val link = "https:" + i.childNode(1).attr("src")
                        imgUrlList.add(link)
                        mainAdapter.submitList(imgUrlList)
                    }
                }
                return NodeFilter.FilterResult.CONTINUE
            }

            override fun tail(node: Node, depth: Int): NodeFilter.FilterResult {
                return NodeFilter.FilterResult.CONTINUE

            }

        })
    }

    private fun initSliderListener() {

        val touchListener = object : Slider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: Slider) {
            }

            // notify data change
            override fun onStopTrackingTouch(slider: Slider) {
                if (slider.value < 0.25) {
                    binding.slider.value = 0.1f
                    setAdapter(MainAdapter.VIEWTYPE_BIGHOLDER)
                } else if (slider.value in 0.25..0.75) {
                    binding.slider.value = 0.5f
                    setAdapter(MainAdapter.VIEWTYPE_GRID2HOLDER)
                } else {
                    binding.slider.value = 0.9f
                    setAdapter(MainAdapter.VIEWTYPE_GRID4HOLDER)
                }
            }

        }

        binding.slider.addOnSliderTouchListener(touchListener)
    }

    private fun initAdapter() {
        mainAdapter = MainAdapter()
        binding.recyclerview.adapter = mainAdapter
        binding.recyclerview.layoutManager = linearManager
    }

    private fun setAdapter(viewType: Int) {
        mainAdapter.setItemViewType(viewType)
        when (viewType) {
            MainAdapter.VIEWTYPE_BIGHOLDER -> {
                binding.recyclerview.layoutManager = linearManager
            }
            MainAdapter.VIEWTYPE_GRID2HOLDER -> {
                binding.recyclerview.layoutManager = grid2Manager
            }
            MainAdapter.VIEWTYPE_GRID4HOLDER -> {
                binding.recyclerview.layoutManager = grid4Manager
            }
        }
    }
}