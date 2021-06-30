package com.example.youngin.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CustomHeaderInterceptor(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request? = chain.request().newBuilder()
            .addHeader("User-Agent", "SIV_SEARCH: BEAUTY_MOBILE: 1.0: AOS:")
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .build()

        return chain.proceed(request!!)
    }
}