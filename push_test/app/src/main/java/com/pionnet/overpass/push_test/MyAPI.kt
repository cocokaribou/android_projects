package com.pionnet.overpass.push_test

import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyAPI {
    @GET("token/regist")
    fun registerToken(
        @Query("t") token: String
    ): Call<ResponseBody>

    companion object {
        fun getAPIService(): MyAPI {
            val okBuilder = OkHttpClient.Builder()

            okBuilder.apply {
                dispatcher(Dispatcher())
                addInterceptor(Interceptor {
                    val request = it.request().newBuilder().build()
                    return@Interceptor it.proceed(request)
                })
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(10, TimeUnit.SECONDS)
            }

            return Retrofit.Builder()
                .baseUrl("http://10.74.105.65:22121/")
                .client(okBuilder.build())
                .build()
                .create()
//                .client(getUnsafeOkHttpClient()!!)
        }
    }
}