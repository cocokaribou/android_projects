package com.example.app3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.app3.databinding.ActivityMainBinding
import com.kcrimi.tooltipdialog.ToolTipDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTooltipView()
    }

    private fun initTooltipView() {
        binding.tv1.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .title("tooltip dialog")
                .content("tis a content")
                .subtitle("subtitle")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .pointTo(location[0], location[1])
                .show()
        }
        binding.tv2.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .title("tooltip dialog")
                .content("tis a content")
                .subtitle("subtitle")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .pointTo(location[0], location[1])
                .show()
        }
        binding.tv3.setOnClickListener {
            val location = intArrayOf(0, 0)
            it.getLocationInWindow(location)
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .title("tooltip dialog")
                .content("tis a content")
                .subtitle("subtitle")
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
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .title("tooltip dialog")
                .content("tis a content")
                .subtitle("subtitle")
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
            Logger.e("x=${location[0]}, y=${location[1]}")

            ToolTipDialog(this, this)
                .title("tooltip dialog")
                .content("tis a content")
                .subtitle("subtitle")
                .setToolTipListener(object : ToolTipDialog.ToolTipListener {
                    override fun onClickToolTip() {
                    }

                })
                .addPeekThroughView(it)
                .setYPosition(location[1] + it.height)
                .pointTo(location[0], location[1]+it.height)
                .show()
        }
    }
}