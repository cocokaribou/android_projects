package com.example.elandmall_kotlin

import android.app.Application
import android.content.Context
import com.example.elandmall_kotlin.util.Logger
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

class BaseApplication : Application() {
    var isAppRunning = false

    companion object {
        @Volatile
        lateinit var instance: BaseApplication

        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Logger.initTag("youngin")

        initFlipper()
    }

    private fun initFlipper() {
        SoLoader.init(context, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            AndroidFlipperClient.getInstance(context).apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        context.applicationContext,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(NetworkFlipperPlugin())
                addPlugin(SharedPreferencesFlipperPlugin(context))

            }.start()
        }
    }

    fun clearData() {
        isAppRunning = false
    }
}