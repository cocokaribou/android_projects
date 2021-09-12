package com.pionnet.overpass.push_test

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.net.URLDecoder

class MainActivity : AppCompatActivity() {
    private var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val mNotificationManagerCompat = NotificationManagerCompat.from(applicationContext)

        // 알림 수신 여부 체크
        val areNotificationsEnabled: Boolean = mNotificationManagerCompat.areNotificationsEnabled()
        if (!areNotificationsEnabled) {
            val snackbar = Snackbar
                    .make(
                            mainLayout,
                            "알림을 활성화해주세요",
                            Snackbar.LENGTH_LONG
                    )
                    .setAction("설정") {
                        openNotificationSettings()
                    }
            snackbar.show()
            return
        }

        // 토큰 전송
        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging.token.addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.e("FCM Log", "instance 초기화 실패")
                Log.e("FCM Log", "exception: ${it.exception}")
                Log.e("FCM Log", "result: ${it.result}")
            } else {
                Log.e("FCM messaging token", "${it.result}")
                Log.e("FCM messaging token", "isString = ${it.result is String}")
                token = it.result!!
                registerTokenAPI()
            }
        }

        // 구독
        firebaseMessaging.subscribeToTopic("testTopic")
    }

    private fun openNotificationSettings() {
        // Links to this app's notification settings.
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", packageName)
        intent.putExtra("app_uid", applicationInfo.uid)

        // for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

        startActivity(intent)
    }

    private fun registerTokenAPI() {
        val myAPI = MyAPI.getAPIService()
        val callBack: Call<ResponseBody> = myAPI.registerToken(token)
        callBack.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val resultBody = URLDecoder.decode(response.body()?.string(), "utf-8")
                    Log.e("요청결과 헤더", "${response.raw().headers}")
                    AlertDialog.Builder(this@MainActivity).setMessage("요청결과: $resultBody")
                            .setPositiveButton("확인") { _, _ ->
                            }.show()
                }
                return
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Throwable", "$t")
                AlertDialog.Builder(this@MainActivity).setMessage("요청결과: 실패")
                        .setPositiveButton("확인") { _, _ ->
                        }.show()
            }
        })
    }
}