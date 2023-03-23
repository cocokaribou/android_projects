package com.example.elandmall_kotlin.ui.goods

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.databinding.ActivityGoodsBinding
import com.example.elandmall_kotlin.model.GoodsModuleType
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.view.GoodsCommonTabView
import com.example.elandmall_kotlin.view.TabListener

class GoodsActivity : BaseActivity(), TabListener {

    private val binding get() = _binding!!
    private var _binding: ActivityGoodsBinding? = null

    private val viewModel: GoodsViewModel by viewModels()
    private val mAdapter by lazy { GoodsAdapter() }

    private val goodsTab by lazy { GoodsCommonTabView(this) }

    private val scrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // scroll to top button
                if (dy > 0) {
                    binding.top.visibility = View.VISIBLE
                } else if (!recyclerView.canScrollVertically(-1)) {
                    binding.top.visibility = View.GONE
                }

                // sticky tab
                val layoutManager = binding.list.layoutManager as? LinearLayoutManager ?: return
                val firstVisiblePos = layoutManager.findFirstVisibleItemPosition()
                val tabPos = mAdapter.currentList.indexOfFirst { it.type == GoodsModuleType.GOODS_TAB }

                if (mAdapter.itemCount != -1 && tabPos <= firstVisiblePos) {
                    goodsTab.visibility = View.VISIBLE
                } else {
                    goodsTab.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() = with(binding) {
        list.apply {
            adapter = mAdapter
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            (layoutManager as LinearLayoutManager).recycleChildrenOnDetach = true
            addOnScrollListener(scrollListener)
        }

        top.setOnClickListener {
            top.visibility = View.GONE
            list.scrollToPosition(0)
        }

        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addContentView(goodsTab, layoutParams)

        goodsTab.setCallback(this@GoodsActivity)
        viewModel.setCallback(this@GoodsActivity)
    }

    private fun initObserve() = with(viewModel) {
        EventBus.linkEvent.observe(this@GoodsActivity) {
            it.getIfNotHandled()?.let { event ->
                super.onLinkEvent(event)
                finish()
            }
        }

        uiList.observe(this@GoodsActivity) {
            mAdapter.submitList(it)
        }

        stickyData.observe(this@GoodsActivity) {
            val reviewCount = it["reviewCount"] as? Int
            val qnaCount = it["qnaCount"] as? Int

            goodsTab.updateCount(reviewCount, qnaCount)
        }

        currentIndex.observe(this@GoodsActivity) { index ->
            goodsTab.selectTab(index)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTabSelect(index: Int) {
        viewModel.updateTabInner(index)
    }
}