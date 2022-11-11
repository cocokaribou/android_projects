package com.example.custom_logview.util

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.viewbinding.ViewBinding
import com.example.custom_logview.databinding.ViewLogBinding

abstract class EnterListener : View.OnKeyListener {
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            onEnter()
            return true
        }
        return false
    }

    abstract fun onEnter()
}

abstract class TextFocusListener : View.OnFocusChangeListener {
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        v?.let { view ->
            if (!view.isFocused) onFocusOut()
        }
    }
    abstract fun onFocusOut()
}