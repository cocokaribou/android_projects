package com.example.elandmall_kotlin.ui.goods

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() = with(binding){
        list.adapter = mAdapter
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