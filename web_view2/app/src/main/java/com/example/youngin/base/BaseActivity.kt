package com.example.youngin.base

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.youngin.network.CustomHeaderInterceptor
import com.example.youngin.network.HttpUrl
import com.example.youngin.network.MyAPI
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pionnet.overpass.extension.hasPermission
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest

open class BaseActivity : AppCompatActivity() {
    var mLandingInfo: String? = null
    var mLandingType = 0

    val tag = javaClass.simpleName
    private val permissionCall = 100


    fun callIntent(url: String) {
        val permission = android.Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                permissionCall
            )
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
            startActivity(intent)
        }
    }
}