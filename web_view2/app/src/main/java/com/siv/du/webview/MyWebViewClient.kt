package com.siv.du.webview

import android.Manifest
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
import androidx.core.content.ContextCompat
import com.siv.du.activity.MainActivity
import com.siv.du.activity.SettingActivity
import com.siv.du.common.CommonConst
import com.sivillage.beauty.webview.PaymentModule

/**
 * 커스텀 웹뷰 클라이언트
 * - url 처리 shouldOverrideUrlLoading()
 */
class MyWebViewClient(private val context: Context) : WebViewClient() {
    private val tag = javaClass.simpleName
    private var paymentModule = PaymentModule(context)

    interface WebViewListener{
        fun onPageStarted(url: String)
        fun onPageShouldOverride(url: String)
        fun onPageFinished(url:String)
        fun call(tel:String)
    }
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        Log.e("$tag checker!", "url: $url")
        val uri: Uri = Uri.parse(url)
        val scheme = uri.scheme
        val host = uri.host
        Log.e("$tag checker!", "scheme: $scheme")

        when (scheme) {
            /* scheme이 siecbeauty:// 일 때 */
            CommonConst.SCHEME_BRIDGE -> {
                when (host) {
                    "setting" -> {
                        val intent =
                            Intent(context.applicationContext, SettingActivity::class.java)
                        context.startActivity(intent)
                        return true

                    }
                    "external_browser" -> {
                        val queryMap = mutableMapOf<String, String>()
                        if (!uri.query.isNullOrEmpty()) {
                            uri.queryParameterNames.forEach { queryName ->
                                val queryParam = uri.getQueryParameter(queryName) ?: ""
                                queryMap[queryName] = queryParam
                            }
                        }
                        if (!queryMap["link"].isNullOrEmpty()) {
                            //외부 브라우저 새 창으로
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(queryMap["link"]))
                            context.startActivity(intent)

                            //외부 브라우저 웹뷰로
//                            view.loadUrl(queryMap["link"]!!)
                            return true
                        }
                    }
                }
            }

            /* scheme이 tel:// 일 때 */
            "tel" -> {
                (context as MainActivity).callIntent(url)
                return true
            }

            /* scheme이 mailto:// 일 때 */
            "mailto" -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
                context.startActivity(intent)
                return true

            }
        }

        /* scheme이 intent:// 일 때 */
        /* 1. 결제패키지 */
        if (paymentModule.processPaymentQuery(view, url)) {
            return true
        }

        /* 2. 카카오톡, 카카오스토리 공유 */
        if (scheme == "intent") {
            if (url.startsWith("intent:kakaolink") or url.startsWith("intent:storylink")) {
                val uri = Uri.parse(url.replace("intent:", ""))
                try {
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                } catch (e: java.lang.Exception) {
                    var target: String? = null
                    if (url.startsWith("intent:storylink")) {
                        target = "com.kakao.story"
                    } else if (url.startsWith("intent:kakaolink")) {
                        target = "com.kakao.talk"
                    }
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$target")
                    context.startActivity(intent)
                }
                return true
            }
        }

        /* url이 http, https 일 때 */
        if (URLUtil.isNetworkUrl(url)) {
            view.loadUrl(url)
            return true
        }
        Log.e("$tag checker!", "failed to load url")
        return false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) { //마이페이지가 여길 타는게 아니라 로그인 정보를 못 받아오는듯.. 쿠키 관련인가......
        super.onPageFinished(view, url)
    }

    private fun checkPermissionCall(): Boolean {
        val callPermission = Manifest.permission.CALL_PHONE
        return ContextCompat.checkSelfPermission(
            context as Activity,
            callPermission
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun askPermissionCall() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.CALL_PHONE),
            1001
        )
    }

}