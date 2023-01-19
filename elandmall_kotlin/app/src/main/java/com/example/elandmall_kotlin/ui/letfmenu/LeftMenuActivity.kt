package com.example.elandmall_kotlin.ui.letfmenu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.elandmall_kotlin.BaseActivity
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.common.CommonConst
import com.example.elandmall_kotlin.databinding.ActivityLeftMenuBinding
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.LinkEvent

class LeftMenuActivity : BaseActivity() {
    val viewModel: LeftMenuViewModel by viewModels()
    val binding by lazy { ActivityLeftMenuBinding.inflate(layoutInflater) }

    private val mAdapter by lazy { LeftMenuAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isSavedInstanceState(savedInstanceState)) {
            return
        }
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_none)

        setContentView(binding.root)

        initUI()
        initObserve()
    }

    private fun initUI() = with(binding) {
        list.adapter = mAdapter

        close.setOnClickListener { finish() }
    }

    private fun initObserve() {
        observeLinkEvent()
        observeUIData()
    }

    private fun observeLinkEvent() {
        EventBus.linkEvent.observe(this) {
            it.getIfNotHandled()?.let { event ->
                onLinkEvent(event)
                setResult(RESULT_OK, Intent().putExtra(CommonConst.EXTRA_LINK_EVENT, event))
                finish()
            }
        }
    }

    private fun observeUIData() {
        viewModel.loginData.observe(this) {
            initLoginUI(it)
        }
        viewModel.topMenuData.observe(this) {
            initTopMenuUI(it)
        }
        viewModel.uiList.observe(this) {
            mAdapter.submitList(it)
        }
    }

    private fun initLoginUI(loginInfo: LeftMenuResponse.LoginInfo?) = with(binding){
        if (loginInfo?.isLogin == true){
            userNm.text = loginInfo.mbrNm
            userNm.visibility = View.VISIBLE

            login.setText(R.string.lnb_logout)
        } else {
            userNm.visibility = View.GONE
            login.setText(R.string.lnb_login)
        }
    }

    private fun initTopMenuUI(list: List<LeftMenuResponse.NavCatTopMenu?>) = with(binding) {
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

