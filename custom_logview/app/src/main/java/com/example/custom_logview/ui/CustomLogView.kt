package com.example.custom_logview.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.custom_logview.R
import com.example.custom_logview.databinding.ViewLogBinding
import com.example.custom_logview.util.EnterListener
import com.example.custom_logview.util.TextFocusListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CustomLogView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : LinearLayout(context, attrs, def) {

    private val mAdapter by lazy { LogAdapter() }
    private val binding: ViewLogBinding by lazy {
        ViewLogBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val bottomLogView: BottomSheetBehavior<CustomLogView> by lazy {
        BottomSheetBehavior.from(this@CustomLogView)
    }

    init {
        binding.initView()
    }

    fun submitList(list: List<LogData>) {
        mAdapter.submitList(list)
        CoroutineScope(Dispatchers.Main).launch {
            delay(200)
            binding.list.scrollToPosition(0)
        }
    }

    fun toggleLogView() {
        when (bottomLogView.state) {
            BottomSheetBehavior.STATE_COLLAPSED -> bottomLogView.state = BottomSheetBehavior.STATE_HIDDEN
            BottomSheetBehavior.STATE_HIDDEN -> bottomLogView.state = BottomSheetBehavior.STATE_COLLAPSED
            BottomSheetBehavior.STATE_EXPANDED -> bottomLogView.state = BottomSheetBehavior.STATE_COLLAPSED
            else -> {}
        }
    }

    fun initBottomTab() {
        bottomLogView.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> CustomLog.i("---------------- logview closed ----------------")
                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset > 0) {
                    // expanded ~ collapse
                } else if (slideOffset < 0) {
                    // collapse ~ hidden
                } else if (slideOffset == 0f) {
                    // collapsed
                }
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun ViewLogBinding.initView() {
        list.adapter = mAdapter

        root.setOnTouchListener { v, _ ->
            if (v !is EditText) {
                hideKeyboard()
            }
            true
        }

        clear.setOnClickListener {
            val constraints = ConstraintSet()
            constraints.clone(binding.root.context, R.layout.view_log2)
            constraints.applyTo(binding.root)
        }

        save.setOnClickListener {
            toast("save log to internal storage")
            TODO ("save log")
        }

        search.apply {
            setOnKeyListener(object: EnterListener() {
                override fun onEnter() {
                    hideKeyboard()
                    CustomLog.search(search.text.toString())
                    clearInput()
                }
            })
            onFocusChangeListener = object: TextFocusListener() {
                override fun onFocusOut() {
                    hideKeyboard()
                }
            }
        }
    }

    private fun toast(msg: String) {
        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            setText(msg)
        }.show()
    }

    private fun hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        (context as? AppCompatActivity)?.currentFocus?.let { view ->
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }

    private fun ViewLogBinding.clearInput() {
        search.text.clear()
    }
}