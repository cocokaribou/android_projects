package com.youngin.lunch.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.youngin.lunch.*
import com.youngin.lunch.databinding.ActivityMainBinding
import com.youngin.lunch.model.Category
import com.youngin.lunch.model.Store

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()
    val mAdapter by lazy { StoreImgAdapter() }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView(): Unit = with(binding) {
        menu.text = "오늘 점심은 ${Category.selectedCate.name}이다!!"
        scrollview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            setOnClickListener {
                Logger.v("youngin clicked")
                viewModel.getListData()
            }
        }
    }

    override fun initObserve() {
        viewModel.mainData.observe(this) {
            Store.selectedStore = it?.stoList?.getOrNull(0)
            Logger.v("youngin 선택됨 ${Store.selectedStore}")
            it?.stoList?.let {
                Logger.v("youngin $it")
                mAdapter.submitList(it)
            }
        }
    }
}