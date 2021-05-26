package com.example.aos_framework_demo

import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aos_framework_demo.databinding.ActivityMainBinding
import com.example.aos_framework_demo.dialog.CustomDialog
import com.pionnet.overpass.extension.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toast: Toast
    private val tempArr = listOf(
        "https://img1.png",
        "https://img2.png",
        "https://img3.png",
        "https://img4.png",
        "https://img5.png"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 토스트 띄우는 클릭리스너
         */
        val toastClickListener: View.OnClickListener = View.OnClickListener {
            val toastMessage = when (it.id) {

                binding.btnSetSplashImg.id -> {
                    setSplashImgURL(this.tempArr, true)
                }

                binding.btnCheckUpdate.id -> {
                    val newVer = "2.5.0"
                    val checker = isUpdate(getAppVersion(applicationContext), newVer)
                    when (checker) {
                        true -> "업데이트가 필요합니다"
                        false -> "업데이트가 완료됐습니다"
                    }
                }

                binding.btnGetJson.id -> {
                    getJsonFileToString("membership.json", this@MainActivity)
                }

                binding.btnToSimpleStr.id -> {
                    Date().toSimpleString()
                }

                binding.btnGetCurrTime.id -> {
                    getCurrentTime()
                }

                else -> {
                    ""
                }

            }
            toast = Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.btnCheckUpdate.setOnClickListener(toastClickListener)
        binding.btnSetSplashImg.setOnClickListener(toastClickListener)
        binding.btnGetJson.setOnClickListener(toastClickListener)
        binding.btnToSimpleStr.setOnClickListener(toastClickListener)
        binding.btnGetCurrTime.setOnClickListener(toastClickListener)

        /**
         * 다이얼로그 띄우는 클릭리스너
         */
        val dialogClickListener: View.OnClickListener = View.OnClickListener {
            val dlg = CustomDialog(this@MainActivity)
            dlg.start(viewId = it.id)
        }

        binding.btnAddDate.setOnClickListener(dialogClickListener)
        binding.btnDpToPx.setOnClickListener(dialogClickListener)
        binding.btnPriceFormat.setOnClickListener(dialogClickListener)
        binding.btnProductCnt.setOnClickListener(dialogClickListener)


        /**
         * 입력창 키 리스너
         */
        val txtBold = binding.txtBold
        val txtUnderLine = binding.txtUnderLine
        val txtStroke = binding.txtStroke

        val mOnKeyListener = View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                /**
                 * Text 입력
                 */
                val input = binding.editTextView.text.toString()
                txtBold.text = input
                txtUnderLine.text = input
                txtStroke.text = input

                txtBold.setBoldText()
                txtUnderLine.setUnderLine()
                txtStroke.setPriceStroke(size = binding.txtStroke.length(), isExist = true)

                /**
                 * Margin 입력
                 */
                val marginStr = binding.editTextViewMargin.text.toString()
                var margin = 0
                try {
                    margin = Integer.parseInt(marginStr)
                } catch (e: Exception) {
                }
                txtBold.setDynamicLeftMargin(margin)
                txtUnderLine.setDynamicLeftMargin(margin)
                txtStroke.setDynamicLeftMargin(margin)
            }
            false
        }

        binding.editTextView.setOnKeyListener(mOnKeyListener)
        binding.editTextViewMargin.setOnKeyListener(mOnKeyListener)

    }
}
