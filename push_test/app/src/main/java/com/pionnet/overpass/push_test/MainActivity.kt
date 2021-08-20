package com.pionnet.overpass.push_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
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
                    .setAction("설정"){
                        openNotificationSettings()
                    }
            snackbar.show()
            return
        }

        // 토큰 전송
        val firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseMessaging.token.addOnCompleteListener{
            if(!it.isSuccessful){
                Log.e("FCM Log", "instance 초기화 실패")
            }else{
                Log.e("FCM messaging token", "${it.result}")
                val myAPI = MyAPI.getAPIService()
                myAPI.registerToken(it.result!!)
            }
        }

        // 구독
        firebaseMessaging.subscribeToTopic("testTopic")
    }
    private fun openNotificationSettings(){
        // Links to this app's notification settings.
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", packageName)
        intent.putExtra("app_uid", applicationInfo.uid)

        // for Android 8 and above
        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

        startActivity(intent)
    }
}