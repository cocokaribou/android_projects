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
    private val mTelNo: String? = null  //? 핸드폰번호가..아 전화거는 intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //webChromeClient filechooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            12 -> {
                Log.e("로그나 찍어", "냠냠")
            }
            10008 -> {
                Log.e("$tag", "메인으로 잘 빠집니다")
            }
        }
//        https://www.blueswt.com/118
    }


    fun getAPIService(): MyAPI {
        val okBuilder = OkHttpClient.Builder()

        val myDispatcher = Dispatcher()
        myDispatcher.maxRequests = 8
        myDispatcher.maxRequestsPerHost = 8

        val headerInterceptor = CustomHeaderInterceptor(this)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okBuilder.apply {
            dispatcher(myDispatcher)

            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)
            addNetworkInterceptor(StethoInterceptor())

            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
        }

        val builder = Retrofit.Builder()
        builder.baseUrl(HttpUrl.serverUrl)
        builder.addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())

        val retrofit: Retrofit = builder.build()
        return retrofit.create(MyAPI::class.java)
    }


    open fun callIntent(url: String) {
        val permission = android.Manifest.permission.CALL_PHONE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                100
            )
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
            startActivity(intent)
        }
    }
}