package com.example.youngin.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface MyAPI {
    @POST("/app/intro_beauty.siv")
    fun getIntro(): Call<ResponseBody>

}
