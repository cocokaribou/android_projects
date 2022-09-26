package com.youngin.lunch.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.youngin.lunch.BaseActivity
import com.youngin.lunch.Logger
import com.youngin.lunch.R
import com.youngin.lunch.databinding.ActivityMainBinding
import okhttp3.internal.platform.android.AndroidLogHandler
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    val categories by lazy {
        mapOf(
            1 to getString(R.string.korean),
            2 to getString(R.string.chinese),
            3 to getString(R.string.western),
            4 to getString(R.string.japanese)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rand = Random().nextInt(4)+1
        binding.menu.text = "오늘 점심은 ${categories[rand]}이다!!"
        viewModel.mainData.observe(this) {
            it?.todayRecommend?.let {
                Logger.v("where you at ${it.stoImgUrl}")
//                Glide.with(binding.root).load(it.stoImgUrl).centerCrop().into(binding.storeImage)
//                Glide.with(binding.root).load(it.stoImgUrl).centerCrop().into(binding.storeImage2)
            } ?: run {
            }
        }

    }
}