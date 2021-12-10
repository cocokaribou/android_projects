package com.pionnet.overpass.push_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    lateinit var myAPI: MyAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAPI = MyAPI.getAPIService()

        // fcm 토큰 -> 내부 서버로 전송
        registerTokenAPI()

        // message.data payload -> intent.extra 처리
        processLink()
        requestPushOpenCount()
    }

    override fun onResume() {
        super.onResume()

        // 시스템 알림 허용여부 체크
        checkSystemNoti()
    }

    // fcm 토큰 -> 내부 서버로 전송
    private fun registerTokenAPI() {
        var token: String
        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging.token.addOnCompleteListener {
            when {
                !it.isSuccessful -> {
                    Logger.e("instance 초기화 실패")
                    Logger.e("exception: ${it.exception}")
                    Logger.e("result: ${it.result}")
                }
                it.isSuccessful -> {
                    Logger.e("token: ${it.result}")
                    token = it.result!!
                    val callBack = myAPI.registerTokenApi(token, CommonConst.target_app)
                    callBack.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                Logger.e("code: ${response.code()}\t${response.body()?.string()}")
                            } else {
                                Logger.e("code: ${response.code()}\t${JSONObject(response.errorBody()?.string()).get("error_message")}")
                            }
                            return
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Logger.e("t: $t")
                        }
                    })
                }
            }
        }
    }

    // 시스템 알림 허용여부 체크
    private fun checkSystemNoti() {
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val mNotificationManagerCompat = NotificationManagerCompat.from(applicationContext)

        val areNotificationsEnabled: Boolean =
            mNotificationManagerCompat.areNotificationsEnabled()
        if (!areNotificationsEnabled) {
            val snackbar = Snackbar
                .make(
                    mainLayout,
                    getString(R.string.msg_system_noti),
                    Snackbar.LENGTH_LONG
                )
                .setAction(getString(R.string.setting)) {
                    openNotificationSettings()
                }
            snackbar.show()
            return
        }
    }

    private fun openNotificationSettings() {
        // Links to this app's notification settings.
        val intent = Intent()
        intent.action = getString(R.string.intent_action_notification)
        intent.putExtra(getString(R.string.intent_extra_package), packageName)
        intent.putExtra(getString(R.string.intent_extra_uid), applicationInfo.uid)

        // for Android 8 and above
        intent.putExtra(getString(R.string.intent_extra_package_8), packageName)

        startActivity(intent)
    }

    // message.data["link_url"] 페이로드 처리
    private fun processLink() {
        val webView = findViewById<WebView>(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.webViewClient = WebViewClient()

        val linkUrl = intent.getStringExtra(CommonConst.link_url)
        linkUrl?.let { url ->
            webView.loadUrl(url)
        }
    }

    // message.data["transaction_id"] 페이로드 처리
    // message 읽음 처리
    private fun requestPushOpenCount() {
        val transactionId = intent.getStringExtra(CommonConst.transaction_id)

        transactionId?.let { id ->
            val callBack = myAPI.checkPushOpen(id)
            callBack.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Logger.e("code: ${response.code()}\t${response.body()?.string()}")
                    } else {
                        Logger.e("code: ${response.code()}\t${JSONObject(response.errorBody()?.string()).get("error_message")}")
                    }
                    return
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Logger.e("t: $t")
                }
            })
        }
    }
}