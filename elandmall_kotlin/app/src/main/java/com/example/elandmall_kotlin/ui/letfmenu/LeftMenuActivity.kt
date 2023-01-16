package com.example.elandmall_kotlin.ui.letfmenu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.common.CommonConst
import com.example.elandmall_kotlin.databinding.ActivityLeftMenuBinding
import com.example.elandmall_kotlin.model.CategoryResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent
import com.example.elandmall_kotlin.util.Logger

class LeftMenuActivity : BaseActivity() {
    val viewModel: LeftMenuViewModel by viewModels()

    //    val binding by lazy { ActivityLeftMenuBinding.inflate(layoutInflater) }
    lateinit var binding: ActivityLeftMenuBinding

    private val mAdapter by lazy { LeftMenuAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }
        binding = ActivityLeftMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() = with(binding) {
        list.adapter = mAdapter
    }

    private fun initObserve() {
        observeLinkEvent()
        observeUIData()
    }

    fun observeLinkEvent() {
        EventBus.linkEvent.observe(this) {
            it.getIfNotHandled()?.let { event ->
                setResult(RESULT_OK, Intent().putExtra(CommonConst.EXTRA_LINK_EVENT, event))
                finish()
            }
        }
    }

    fun observeUIData() {
        viewModel.topMenuData.observe(this) {
            initTopMenu(it)
        }
        viewModel.uiList.observe(this) {
            mAdapter.submitList(it)
        }
    }

    private fun initTopMenu(list: List<CategoryResponse.Data.NavCatTopMenu?>) = with(binding) {
        btn1.apply {
            title = list[0]?.menuNm ?: ""
            click = { EventBus.fire(LinkEvent(list[0]?.linkUrl)) }
        }
        btn2.apply {
            title = list[1]?.menuNm ?: ""
            click = { EventBus.fire(LinkEvent(list[1]?.linkUrl)) }
        }
        btn3.apply {
            title = list[2]?.menuNm ?: ""
            count = list[2]?.cartCnt
            click = { EventBus.fire(LinkEvent(list[2]?.linkUrl)) }
        }
        btn4.apply {
            title = list[3]?.menuNm ?: ""
            click = { EventBus.fire(LinkEvent(list[3]?.linkUrl)) }
        }
        btn5.apply {
            title = list[4]?.menuNm ?: ""
            count = list[4]?.cupnCnt
            click = { EventBus.fire(LinkEvent(list[4]?.linkUrl)) }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_none, R.anim.slide_left_out)
    }
}

