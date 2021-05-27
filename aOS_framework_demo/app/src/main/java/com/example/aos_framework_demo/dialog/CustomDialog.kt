package com.example.aos_framework_demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.aos_framework_demo.R
import com.pionnet.overpass.extension.*

class CustomDialog(context: Context) {
    private val dialog = Dialog(context) //부모 액티비티의 context
    private val mContext = context //부모 액티비티의 context를 저장

    fun start(viewId: Int) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_dialog)
        dialog.show()

        val btnConfirm = dialog.findViewById<TextView>(R.id.btn_confirm)
        val editInput = dialog.findViewById<EditText>(R.id.editTxt_input)
        val txtResult = dialog.findViewById<TextView>(R.id.txt_result)
        val switch = dialog.findViewById<SwitchCompat>(R.id.switch_stroke)

        when (viewId) {
            R.id.btn_addDate -> txtResult.text = "더할 일수를 입력하세요"
            R.id.btn_dpToPx -> txtResult.text = "dp값을 입력하세요"
            R.id.btn_priceFormat -> txtResult.text = "천 단위를 입력하세요"
            R.id.btn_productCnt -> txtResult.text = "만 단위를 입력하세요"
            R.id.btn_setStroke -> {
                txtResult.text = "액수를 입력하세요"
                switch.visibility = View.VISIBLE
            }
            else -> ""
        }


        /**
         * Text extension
         */
        editInput.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KEYCODE_ENTER) {
                //null check logic refactored
                if (!editInput.text.toString().isNullOrEmpty()) {
                    val inputString = editInput.text.toString()
                    val inputNum =
                        editInput.text.toString().toIntOrNull() ?: 0 //NumberFormatException 방지

                    when (viewId) {

                        R.id.btn_addDate -> {
                            txtResult.text =
                                "$inputNum 일 후: ${getAddDateString("yyyy/MM/dd", inputNum)}"
                        }
                        R.id.btn_dpToPx -> {
                            txtResult.text =
                                "$inputNum dp -> ${inputNum.dpToPx(mContext)} px"
                        }
                        R.id.btn_priceFormat -> {
                            txtResult.text =
                                priceFormat(inputString)
                        }
                        R.id.btn_productCnt -> {
                            txtResult.text =
                                productCnt(inputNum.toLong())
                        }
                        R.id.btn_setStroke -> {
                            txtResult.text = inputString
                            txtResult.setPriceStroke(20, switch.isChecked)
                        }
                        else -> {
                        }
                    }
                }
            }
            false
        }
        btnConfirm.setOnClickListener {
            if (!editInput.text.toString().isNullOrEmpty()) {
                val inputString = editInput.text.toString()
                val inputNum =
                    editInput.text.toString().toIntOrNull() ?: 0 //NumberFormatException 방지

                when (viewId) {

                    R.id.btn_addDate -> {
                        txtResult.text =
                            "$inputNum 일 후: ${getAddDateString("yyyy/MM/dd", inputNum)}"
                    }
                    R.id.btn_dpToPx -> {
                        txtResult.text =
                            "$inputNum dp -> ${inputNum.dpToPx(mContext)} px"
                    }
                    R.id.btn_priceFormat -> {
                        txtResult.text =
                            priceFormat(inputString)
                    }
                    R.id.btn_productCnt -> {
                        txtResult.text =
                            productCnt(inputNum.toLong())
                    }
                    R.id.btn_setStroke -> {
                        txtResult.text = inputString
                        txtResult.setPriceStroke(20, switch.isChecked)
                    }
                    else -> {
                    }
                }
            }
        }
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            txtResult.text = editInput.text.toString()
            txtResult.setPriceStroke(20, isChecked)
        }
    }
}