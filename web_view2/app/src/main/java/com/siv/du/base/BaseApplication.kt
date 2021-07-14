package com.siv.du.base

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatDialog
import com.facebook.stetho.Stetho
import com.siv.du.common.CommonConst
import com.siv.du.common.PreferenceManager

/**
 * 어플리케이션 베이스
 * - landing
 * - logging 서비스 초기화
 */
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
        var showSplash = true

        fun getServerType():Int{ //타입을 int로 리턴, 네트워크
            return PreferenceManager.getInt(instance, CommonConst.PREF_KEY_TEST_SERVER, CommonConst.SERVER_TYPE_TOBE)
        }
    }

    /* wiselog init */

    /* appsflyer init */
}