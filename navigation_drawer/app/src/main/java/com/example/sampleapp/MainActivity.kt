package com.example.sampleapp

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
    private lateinit var mainAdapter: MainAdapter

    val imgUrlList = mutableListOf<String>()

    private val linearManager = LinearLayoutManager(this)
    private val grid2Manager = GridLayoutManager(this, 2)
    private val grid4Manager = GridLayoutManager(this, 4)

    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
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
                if (!slider.isFocused) {
                    setHolder(MainAdapter.VIEWTYPE_BIGHOLDER)
                }
                if (slider.value < 0.25) {
                    binding.slider.value = 0.1f
                    setHolder(MainAdapter.VIEWTYPE_BIGHOLDER)

                } else if (slider.value in 0.25..0.75) {
                    binding.slider.value = 0.5f
                    setHolder(MainAdapter.VIEWTYPE_GRID2HOLDER)

                } else {
                    binding.slider.value = 0.9f
                    setHolder(MainAdapter.VIEWTYPE_GRID4HOLDER)
                }
            }

        }

        binding.slider.addOnSliderTouchListener(touchListener)
    }

    private fun initAdapter() {
        mainAdapter = MainAdapter()
        setHolder(MainAdapter.VIEWTYPE_BIGHOLDER)

        binding.recyclerview.adapter = mainAdapter
        binding.recyclerview.layoutManager = linearManager
        binding.recyclerview.addOnScrollListener(adapterScrollListener)
    }

    private fun setHolder(viewType: Int) {
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

    private val adapterScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    Logger.e("idle")
                }
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    Logger.e("dragged")
                }
                RecyclerView.SCROLL_STATE_SETTLING -> {
                    Logger.e("settled")
                }
            }
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }
    }

}