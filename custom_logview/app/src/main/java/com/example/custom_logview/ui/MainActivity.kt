package com.example.custom_logview.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.custom_logview.CustomLog
import com.example.custom_logview.LogBottomSheetFragment
import com.example.custom_logview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
//    val logview by lazy {
//        CustomLogView(this).apply {
//            id = View.generateViewId()
//        }
//    }
    private val logBottomSheet: LogBottomSheetFragment by lazy { LogBottomSheetFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CustomLog.d("debug log!")
        CustomLog.w("warning log!")
        CustomLog.i("info log!")
        setContentView(binding.root)

        CustomLog.logLiveData.observe(this) {
//            logview.submitList(it)
            binding.logview.submitList(it)
//            logBottomSheet.submitList(it)
        }

        with(binding) {
            logview.setOnClickListener {
                if (logview.parent != null) {
                    (logview.parent as ViewGroup).removeView(logview)
                }
                root.addView(logview)
//            logBottomSheet.show(supportFragmentManager, "tag")

            }
        }
    }
}