package com.example.aos_framework_demo

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.example.aos_framework_demo.App.Companion.context
import com.pionnet.overpass.extension.dpToPx
import com.pionnet.overpass.extension.getAddDateString

class CustomDialog(context: Context) {
    private val dialog = Dialog(context) //부모 액티비티의 context

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
        edit_input.setHint(content)
        if (txtResize) {
            edit_input.setTextSize(15.toFloat())
        }

        txt_result = dialog.findViewById<TextView>(R.id.txt_result)
        edit_input.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                val result:String = when (btn) {
                    R.id.btn_addDate -> {
                        val date = Integer.parseInt(edit_input.getText().toString())
                        getAddDateString("yyyy:MM:dd", date)
                    }
                    R.id.btn_dpToPx -> {
                        val dp = Integer.parseInt(edit_input.getText().toString())
                        dp.dpToPx(context()).toString()
                    }
                    else -> {"No Result"}
                }

                txt_result.setText(result)
            }
        true
        }
    }
}