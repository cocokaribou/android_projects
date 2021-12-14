package com.example.app5

import android.app.Application
import com.example.app5.data.UserDataManager
import com.facebook.appevents.AppEventsLogger

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)

        UserDataManager.init(this)
        UserDataManager.printUserData()
    }
}