package com.example.elandmall_kotlin.ui.goods

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.EventBus
import com.example.elandmall_kotlin.databinding.ActivityGoodsBinding
import com.example.elandmall_kotlin.model.GoodsModule
import com.example.elandmall_kotlin.model.GoodsModuleType
import com.example.elandmall_kotlin.util.Logger

class GoodsActivity: BaseActivity() {

    private val binding get() = _binding!!
    private var _binding : ActivityGoodsBinding? = null

    private val viewModel : GoodsViewModel by viewModels()
    private val mAdapter by lazy { GoodsAdapter() }

    private val scrollListener by lazy {
        object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.top.visibility = View.VISIBLE
                } else if (!recyclerView.canScrollVertically(-1)) {
                    binding.top.visibility = View.GONE
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

    private fun initUI() = with(binding){
        list.apply {
            adapter = mAdapter
            addOnScrollListener(scrollListener)
        }

        top.setOnClickListener {
            top.visibility = View.GONE
            list.scrollToPosition(0)
        }
    }

    private fun initObserve() = with(viewModel){
        EventBus.linkEvent.observe(this@GoodsActivity) {
            it.getIfNotHandled()?.let { event ->
                super.onLinkEvent(event)
                finish()
            }
        }

        uiList.observe(this@GoodsActivity) {
            mAdapter.submitList(it)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}