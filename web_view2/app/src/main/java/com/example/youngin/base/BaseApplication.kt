package com.example.youngin.base

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatDialog
import com.facebook.stetho.Stetho

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this

        if (0 != ApplicationInfo.FLAG_DEBUGGABLE.also {
                val arg: ApplicationInfo = applicationInfo
                arg.flags = arg.flags and it
            }) { Stetho.initializeWithDefaults(instance)
        }
    }

    companion object {
        lateinit var instance: BaseApplication
        var isAppRunning = false
        private lateinit var progressDialog: AppCompatDialog
        var isReceivingPush = false
        var isProgressOn = false
        var isShowingFlash = true

        fun getServerType():Int{ //타입을 int로 리턴, 네트워크
            return 0
        }
    }
}