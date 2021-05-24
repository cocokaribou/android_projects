package com.example.aos_framework_demo

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.pionnet.overpass.extension.*

class CustomDialog(context: Context) {
    private val dialog = Dialog(context) //부모 액티비티의 context
    private val context = context

    private lateinit var btn_confirm: TextView
    private lateinit var txt_result: TextView

    fun start(content: String, txtResize: Boolean, btn: Int) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_dialog)

        val btn_confirm = dialog.findViewById<TextView>(R.id.btn_confirm)
        btn_confirm.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

        val edit_input = dialog.findViewById<EditText>(R.id.editTxt_input)
        edit_input.hint = content
        if (txtResize) {
            edit_input.textSize = 15.toFloat()
        }

        txt_result = dialog.findViewById<TextView>(R.id.txt_result)
        edit_input.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                when (btn) {
                    R.id.btn_addDate -> {
                        try {
                            val date = Integer.parseInt(edit_input.text.toString())
                            val txt = "$date 일 후: ${getAddDateString("yyyy/MM/dd", date)}"
                            txt_result.text = txt

                        } catch (e: Exception) {
                            txt_result.text = "숫자를 입력하세요"
                        }
                    }
                    R.id.btn_dpToPx -> {
                        try {
                            val num = Integer.parseInt(edit_input.getText().toString())
                            num.dpToPx(context)

                        } catch (e: Exception) {
                            txt_result.text = "숫자를 입력하세요"
                        }
                    }

                    R.id.btn_setBold -> {
                        val txt = edit_input.text.toString()
                        txt_result.text = txt
                        txt_result.setBoldText()
                    }
                    R.id.btn_setStroke -> {
                        val txt = edit_input.text.toString()
                        txt_result.text = txt
                        txt_result.setPriceStroke(16)
                    }
                    R.id.btn_priceFormat -> {
                        try {
                            val num = edit_input.text.toString()
                            val txt = priceFormat(num)
                            txt_result.text = txt
                        } catch(e:Exception){
                            txt_result.text = "숫자를 입력하세요"
                        }

                    }
                    R.id.btn_productCnt -> {
                        try {
                            val num = Integer.parseInt(edit_input.getText().toString())
                            val txt = productCnt(num.toLong())
                            txt_result.text = txt

                        } catch (e: Exception) {
                            txt_result.text = "숫자를 입력하세요"
                        }
                    }

                    else -> {
                        "No Result"
                    }
                }

            }
            true
        }
    }
}