package com.example.shared_viewmodel.util

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.shared_viewmodel.R
import com.google.android.material.snackbar.Snackbar

/**
 * - Custom Toast
 * - Custom Alert Dialog
 * - Custom SnackBar
 */
object PopUpUtil {
    // toast
    fun Context.toast(text: String) {
        Toast(this).apply {
            setText(text)
        }.show()
    }

    fun View.toast(text: String) {
        Toast(context).apply {
            setText(text)
        }.show()
    }

    // dialog
    fun Context.dialog(msg: String, ok: (() -> Unit) = {}, cancel: (() -> Unit)? = null, allowDialogCancel: Boolean = true) {
        AlertDialog.Builder(this, R.style.MyDialogTheme).apply {
            setMessage(msg)
            setPositiveButton(getString(R.string.ok)) { _, _ -> ok.invoke() }

            cancel?.let {
                setNegativeButton(getString(R.string.cancel)) { _, _ -> it.invoke() }
            }
        }.create().apply {
            if (!allowDialogCancel) {
                setOnCancelListener { ok.invoke() }
                setCanceledOnTouchOutside(false)
            }
        }.show()
    }

    // snack bar
    fun View.snackBar(text: String) {
        Snackbar.make(parent as View, text, Snackbar.LENGTH_SHORT).show()
    }
    fun ViewBinding.snackBar(text: String) {
        Snackbar.make(root.parent as View, text, Snackbar.LENGTH_SHORT).show()
    }
}