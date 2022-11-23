package com.example.elandmall_kotlin.util

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import com.example.elandmall_kotlin.R

typealias ButtonClick = (DialogInterface, Int) -> Unit

object DialogUtil {
    fun Activity.popUpDialog(
        msg: String,
        posListener: ButtonClick? = { _, _ -> },
        negListener: ButtonClick? = null
    ) {
        AlertDialog.Builder(this).apply {
            setMessage(msg)
            setPositiveButton(getString(R.string.ok), posListener)
            negListener?.let {
                setNegativeButton(getString(R.string.cancel), negListener)
            }
        }.create().show()
    }
}