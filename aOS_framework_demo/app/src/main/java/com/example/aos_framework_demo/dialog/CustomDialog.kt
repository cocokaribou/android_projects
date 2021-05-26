package com.example.aos_framework_demo.dialog

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.example.aos_framework_demo.R
import com.pionnet.overpass.extension.*
import java.lang.NumberFormatException

class CustomDialog(context: Context) {
    private val dialog = Dialog(context) //부모 액티비티의 context
    private val context = context //부모 액티비티의 context를 저장

    fun start(viewId: Int) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_dialog)

        val btnConfirm = dialog.findViewById<TextView>(R.id.btn_confirm)
        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

        val editInput = dialog.findViewById<EditText>(R.id.editTxt_input)
        var txtResult = dialog.findViewById<TextView>(R.id.txt_result)

        txtResult.text = when (viewId) {
            R.id.btn_addDate -> "더할 일수를 입력하세요"
            R.id.btn_dpToPx -> "dp값을 입력하세요"
            R.id.btn_priceFormat -> "천 단위를 입력하세요"
            R.id.btn_productCnt -> "만 단위를 입력하세요"
            else -> ""
        }

        editInput.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KEYCODE_ENTER) {

                /**
                 * Text extension
                 */
                txtResult.text = when (viewId) {

                    R.id.btn_addDate -> {
                        try {
                            val date = Integer.parseInt(editInput.text.toString())
                            "$date 일 후: ${getAddDateString("yyyy/MM/dd", date)}"

                        } catch (e: Exception) {
                            "숫자를 입력하세요"
                        }
                    }

                    R.id.btn_dpToPx -> {
                        try {
                            val dp = Integer.parseInt(editInput.text.toString())
                            "$dp dp -> ${dp.dpToPx(context).toString()} px"

                        } catch (e: Exception) {
                            "숫자를 입력하세요"
                        }
                    }

                    R.id.btn_priceFormat -> {
                        try {
                            val num = editInput.text.toString()
                            priceFormat(num)
                        } catch (e: Exception) {
                            "숫자를 입력하세요"
                        }

                    }
                    R.id.btn_productCnt -> {
                        try {
                            val num = Integer.parseInt(editInput.text.toString())
                            productCnt(num.toLong())

                        } catch (e: Exception) {
                            "숫자를 입력하세요"
                        }
                    }

                    else -> {
                        ""
                    }
                }
            }
            false
        }
    }
}