package com.example.elandmall_kotlin.api

import com.example.elandmall_kotlin.BuildConfig
import com.example.elandmall_kotlin.base.BaseApplication
import com.example.elandmall_kotlin.common.CommonConst.mainDomain
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiManager {
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

    private val dispatcher = Dispatcher().apply {
        maxRequests = 8
        maxRequestsPerHost = 8
    }

    fun service() : ApiService {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(logging).apply {
                if (BuildConfig.DEBUG) {
                    val flipperOkhttpInterceptor =
                        AndroidFlipperClient.getInstance(BaseApplication.context)
                            .getPluginByClass(NetworkFlipperPlugin::class.java)?.let {
                                FlipperOkhttpInterceptor(it)
                            }
                    flipperOkhttpInterceptor?.let {
                        addNetworkInterceptor(it)
                    }
                }
            }
            .dispatcher(dispatcher)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(mainDomain)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}