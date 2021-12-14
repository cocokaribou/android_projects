package com.example.app5.ui.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.example.app5.data.UserDataManager
import com.facebook.appevents.suggestedevents.ViewOnClickListener

object LoginUiCallbacks {
    fun setTextWatcher(iv: ImageView): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    iv.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    iv.visibility = View.GONE
                }
            }
        }
    }

    fun setFocusChangeListener(iv: ImageView): View.OnFocusChangeListener {
        return View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                iv.visibility = View.GONE
            } else if (hasFocus && (v as EditText).text.isNotEmpty()) {
                iv.visibility = View.VISIBLE
            }
        }
    }

    val enterListener = View.OnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
            UserDataManager.user.id = (v as EditText).text.toString()
            return@OnKeyListener true
        }
        false
    }

    fun EditText.setTextClear(): View.OnClickListener {
        return View.OnClickListener {
            this.text.clear()
        }
    }

    fun CheckBox.toggleCheckbox(): View.OnClickListener{
        return View.OnClickListener {
            this.isChecked = !this.isChecked
        }
    }

}