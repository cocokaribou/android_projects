package com.example.elandmall_kotlin.util

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.example.elandmall_kotlin.databinding.DialogCommonBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val nc = cm.getNetworkCapabilities(cm.activeNetwork)
        (nc != null &&
                (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || nc.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)))
    } else {
        val activeNetworkInfo = cm.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}

fun getJsonFileToString(filePath: String, context: Context): String {
    var result = ""
    val assetManager: AssetManager = context.assets
    try {
        val `is` = assetManager.open(filePath!!)
        val isr = InputStreamReader(`is`)
        val reader = BufferedReader(isr)
        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(
                """$line""".trimIndent()
            )
            line = reader.readLine()
        }
        result = buffer.toString()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return result
}

fun getScreenWidthToPx(): Int = Resources.getSystem().displayMetrics.widthPixels
fun getScreenHeightToPx(): Int = Resources.getSystem().displayMetrics.heightPixels

inline fun <reified T> MutableList<T>.removeRange(range: IntRange): List<T> {
    val fromIndex = range.first
    val toIndex = range.last
    if (fromIndex == toIndex) {
        return listOf()
    }

    if (fromIndex >= size) {
        throw IndexOutOfBoundsException("fromIndex $fromIndex >= size $size")
    }
    if (toIndex > size) {
        throw IndexOutOfBoundsException("toIndex $toIndex > size $size")
    }
    if (fromIndex > toIndex) {
        throw IndexOutOfBoundsException("fromIndex $fromIndex > toIndex $toIndex")
    }

    return filterIndexed { i, _ -> i < fromIndex || i > toIndex }
}

fun String.setHtmlDoc(): String {
    val header = "<html><head>" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\" />" +
            "<style>body {margin: 0px;padding: 0px;}</style> " +
            "</head><body>"
    val closedTag = "</body></html>"

    val htmlText = header + this + closedTag
    val doc: Document = Jsoup.parse(htmlText)

    val hyperLink = doc.getElementsByTag("a")
    if (hyperLink.size > 0) {
        for (i in 0 until hyperLink.size) {
            var href: String = hyperLink.get(i).attr("href")
            if (href.isNotEmpty() && !href.startsWith("http")) {
                href = mainDomain + href
                hyperLink[i].attr("href", href)
            }
        }
    }

    return doc.toString()
}
fun dialogConfirm(
    context: Context,
    msg: String,
    title: String = "",
    okText: String = "확인",
    cancelText: String = "취소",
    okListener: (() -> Unit)? = null,
    cancelListener: (() -> Unit)? = null,
    useSystemTheme: Boolean = false
) {
    Logger.v("${context.javaClass.simpleName}")

    dialogBasic(
        isConfirmType = true,
        context = context,
        msg = msg,
        title = title,
        okText = okText,
        cancelText = cancelText,
        okListener = okListener,
        cancelListener = cancelListener,
        useSystemTheme = useSystemTheme)
}

private fun dialogBasic(
    isConfirmType: Boolean,
    context: Context,
    msg: String,
    title: String = "",
    okText: String = "확인",
    cancelText: String = "취소",
    okListener: (() -> Unit)? = null,
    cancelListener: (() -> Unit)? = null,
    useSystemTheme: Boolean = false
) {
    if (context !is Activity || context.isFinishing) {
        return
    }

    val withTitle = title.isNotEmpty()

    if (useSystemTheme) {
        val builder = AlertDialog.Builder(context)

        if (withTitle) {
            builder.setTitle(title)
        }

        builder.setMessage(msg)

        builder.setPositiveButton(okText) { _, _ ->
            if (okListener != null) {
                okListener()
            }
        }

        if (isConfirmType) {
            builder.setNegativeButton(cancelText) { _, _ ->
                if (cancelListener != null) {
                    cancelListener()
                }
            }
        }

        builder.setCancelable(false).create().show()
        return
    }

    val binding = DialogCommonBinding.inflate(context.layoutInflater)

    val dialog = AlertDialog.Builder(context, R.style.DialogBody)
        .setView(binding.root)
        .setCancelable(false)
        .create()

    if (withTitle) {
        binding.contentWithTitle.let {
            it.title.text = title
            it.desc.text = msg
            it.desc.movementMethod = ScrollingMovementMethod()
            it.root.visibility = View.VISIBLE
        }
    } else {
        binding.contentNoTitle.let {
            it.desc.text = msg
            it.desc.movementMethod = ScrollingMovementMethod()
            it.root.visibility = View.VISIBLE
        }
    }

    binding.ok.apply {
        text = okText
        setOnClickListener {
            if (okListener != null) {
                okListener()
            }
            dialog.dismiss()
        }
    }

    if (isConfirmType) {
        binding.cancel.apply {
            text = cancelText
            setOnClickListener {
                if (cancelListener != null) {
                    cancelListener()
                }
                dialog.dismiss()
            }
        }
    } else {
        binding.cancel.visibility = View.GONE
    }

    dialog.show()
}

