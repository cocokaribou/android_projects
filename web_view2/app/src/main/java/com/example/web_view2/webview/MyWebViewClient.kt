package com.example.web_view2.webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import com.example.web_view2.activity.SettingActivity
import com.example.web_view2.common.CommonConst
import com.pionnet.overpass.extension.hasPermission
import java.util.jar.Manifest

class MyWebViewClient(private val context: Context) : WebViewClient() {
    private var paymentModule = PaymentModule(context)

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val uri: Uri = Uri.parse(url)
        val scheme = uri.scheme
        val host = uri.host

        val queryMap = mutableMapOf<String, String>()
        if (!uri.query.isNullOrEmpty()) {
            uri.queryParameterNames.forEach { queryName ->
                val queryParam = uri.getQueryParameter(queryName) ?: ""
                queryMap[queryName] = queryParam
            }
        }

        if (URLUtil.isNetworkUrl(url)) { //http, https
            view.loadUrl(url)
        }
        if (paymentModule != null && paymentModule!!.processPaymentQuery(view, url))
            return true
        when (scheme) {
            "tel" -> {
                val permission = android.Manifest.permission.CALL_PHONE
                if (hasPermission(context, permission)) {
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(permission),
                        100
                    )
                } else {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    context!!.startActivity(intent)
                }

            }
            "intent" -> {
            }
            "mailto" -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                context!!.startActivity(intent)
            }
            "siecbeauty" -> {
                when (host) {
                    "setting" -> {
                        val intent =
                            Intent(context!!.applicationContext, SettingActivity::class.java)
                        context!!.startActivity(intent)
                    }
                    "external_browser" -> {
                        if (!queryMap["link"].isNullOrEmpty()) {
                            //외부 브라우저 새 창으로
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(queryMap["link"]))
                            context!!.startActivity(intent)

                            //외부 브라우저 웹뷰로
//                    view.loadUrl(queryMap["link"]!!)
                        }
                    }
                }
            }

        }
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }


}