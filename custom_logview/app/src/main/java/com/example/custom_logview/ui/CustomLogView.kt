package com.example.custom_logview.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custom_logview.LogAdapter
import com.example.custom_logview.LogData
import com.example.custom_logview.databinding.ViewLogBinding


class CustomLogView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, def: Int): super(context, attrs, def)

    private val mAdapter by lazy { LogAdapter() }
    private val mLayoutManager by lazy { LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) }
    private val binding: ViewLogBinding by lazy {
        ViewLogBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        Log.v("youngin", "어이가 없네")
        binding.initView()
    }

    fun submitList(list: List<LogData>) {
        mAdapter.submitList(list)
        mLayoutManager.scrollToPosition(0)
    }

    private fun ViewLogBinding.initView() {
        list.adapter = mAdapter
        list.layoutManager = mLayoutManager
    }
}