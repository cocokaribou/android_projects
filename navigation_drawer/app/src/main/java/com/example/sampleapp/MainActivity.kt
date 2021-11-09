package com.example.sampleapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Bundle
import android.view.MotionEvent
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapp.databinding.ActivityMainBinding
import com.google.android.material.slider.Slider
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Node
import org.jsoup.select.NodeFilter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val imgUrlList = mutableListOf<String>()

    private var mainAdapter = MainAdapter()
    private val linearManager = LinearLayoutManager(this)
    private val grid2Manager = GridLayoutManager(this, 2)
    private val grid4Manager = GridLayoutManager(this, 4)

    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initScrollView()

        Thread {
            initData()
        }.start()

        initSliderListener()

    }

    // 나중에
//    fun onInterceptorTouchEvent(event:MotionEvent): Boolean {
//        when(event){
//
//        }
//    }

    private fun initScrollView() {
        binding.main.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
//            Logger.e("oldScrollY=$oldScrollY || scrollY=$scrollY ")
//            Logger.e("v height? ${v.height}")
//            Logger.e("v maxScrollAmount ${v.maxScrollAmount}")
//            Logger.e("v rootView ${v.rootView}")

        })
    }

    private fun initData() {
        val doc: Document =
            Jsoup.connect("https://store.musinsa.com/app/styles/lists?use_yn_360=&style_type=&brand=&model=&tag_no=&max_rt=&min_rt=&display_cnt=60&list_kind=big&sort=date&page=$page")
                .get()

        //TODO paging 처리해서 무한스크롤 리사이클러뷰 만들기

        // filter
        doc.filter(object : NodeFilter {
            override fun head(node: Node, depth: Int): NodeFilter.FilterResult {
                val mChildNode = node.childNodes()
                for (i in mChildNode) {
                    if (i.attr("class") == "style-list-thumbnail") {
                        val link = "https:" + i.childNode(1).attr("src")
                        imgUrlList.add(link)
                    }
                }
                return NodeFilter.FilterResult.CONTINUE
            }

            override fun tail(node: Node, depth: Int): NodeFilter.FilterResult {
                return NodeFilter.FilterResult.CONTINUE

            }

        })

        runOnUiThread {
            initAdapter()
        }
        mainAdapter.submitList(imgUrlList)
    }

    private fun initSliderListener() {

        val touchListener = object : Slider.OnSliderTouchListener {

            override fun onStartTrackingTouch(slider: Slider) {
            }

            // notify data change
            override fun onStopTrackingTouch(slider: Slider) {
                if (slider.value < 0.25) {
                    binding.slider.value = 0.1f
                    setGridCount(1)

                } else if (slider.value in 0.25..0.75) {
                    binding.slider.value = 0.5f
                    setGridCount(2)

                } else {
                    binding.slider.value = 0.9f
                    setGridCount(4)
                }
            }

        }

        binding.slider.addOnSliderTouchListener(touchListener)
    }

    private fun initAdapter() {
        setGridCount(1)

        binding.recyclerview.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(adapterScrollListener)
        }
    }

    private fun setGridCount(gridCount: Int) {
        when (gridCount) {
            1 -> {
                binding.recyclerview.layoutManager = linearManager
                mainAdapter.setGridCount(gridCount)
            }
            2 -> {
                binding.recyclerview.layoutManager = grid2Manager
                mainAdapter.setGridCount(gridCount)
            }
            4 -> {
                binding.recyclerview.layoutManager = grid4Manager
                mainAdapter.setGridCount(gridCount)
            }
        }
    }

    private val adapterScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                }
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                }
                RecyclerView.SCROLL_STATE_SETTLING -> {
                }
            }
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

}