package com.example.app3

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.app3.databinding.ActivityMainBinding
import com.kcrimi.tooltipdialog.ToolTipDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splittable = "httsp://domain?split=this"
        val splitted = splittable.split("splt")[1]
        Log.e("splitted", splitted)

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener,
            ViewTreeObserver.OnGlobalFocusChangeListener {
            override fun onGlobalLayout() {
//                initTooltipView()
            }

            override fun onGlobalFocusChanged(p0: View?, p1: View?) {
            }

        })
        initTooltipView()
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
                .pointTo(location[0] + it.width / 2, location[1] + it.height)
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
                .pointTo(location[0], location[1] - (it.height)*2)
                .show()
        }
        val function: (View) -> Unit = {
            val location = intArrayOf(0, 0)
//            it.getLocationInWindow(location)
//            it.getLocationOnScreen(location)
            it.getLocationInSurface(location)

            ToolTipDialog(this, this)
                .content("pointing down an image view")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .setYPosition(location[1] - (it.height) * 2)
                .pointTo(location[0] + it.width / 2, location[1] - (it.height) * 2)
                .show()
        }
        binding.iv.setOnClickListener(function)
        binding.btn.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)

            ToolTipDialog(this, this)
                .content("peek view")
                .pointTo(location[0] + it.width / 2, location[1] + it.height)
//                .setYPosition(location[1] + it.height)
                .addPeekThroughView(it)
                .show()
        }
    }
}