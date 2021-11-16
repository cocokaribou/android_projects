package com.example.app3

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.app3.databinding.ActivityMainBinding
import com.kcrimi.tooltipdialog.ToolTipDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener,
            ViewTreeObserver.OnGlobalFocusChangeListener {
            override fun onGlobalLayout() {
                initTooltipView()
            }

            override fun onGlobalFocusChanged(p0: View?, p1: View?) {
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initTooltipView() {
        binding.tv1.setOnClickListener {
            val location = intArrayOf(0, 0)
//            it.getLocationInSurface(location)
//            it.getLocationOnScreen(location)
            it.getLocationInWindow(location)

            ToolTipDialog(this, this)
                .content("arrow pointing up")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
//                .pointTo(it.left, it.bottom)
//                .pointTo(0, 0)
                .pointTo(location[0]+it.width/2, location[1])
                .show()
        }

        binding.tv2.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .content("arrow pointing down")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .pointTo(location[0] + it.width / 2, location[1])
                .show()
        }
        binding.tv3.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .content("pointing down")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .pointTo(location[0], location[1])
                .show()
        }
        binding.iv.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)

            ToolTipDialog(this, this)
                .content("pointing down a view")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .setYPosition(location[1])
                .pointTo(location[0], location[1])
                .show()
        }
        binding.btn.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)

            ToolTipDialog(this, this)
                .content("peek view")
                .pointTo(location[0] + it.width / 2, location[1] - it.height)
                .addPeekThroughView(it)
                .show()
        }
    }
}