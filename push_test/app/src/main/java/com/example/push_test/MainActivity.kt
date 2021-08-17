package com.example.push_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
        }
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener {
            if (!it.isSuccessful) {
                Log.w("FCM Log", "getInstance failed: ${it.exception}")
            } else {
                val token = it.result?.token
                Log.e("FCM token", "$token")
                val myAPI = MyAPI.getAPIService()
                myAPI.saveToken(token!!)
            }
        }


    }
}