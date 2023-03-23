package com.example.openapi_test

import com.example.openapi_test.Data.DataVO
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BakeryAPI {

    @GET("{key}/json/LOCALDATA_072218_GS/{page}/{total}")
    fun getBakery(
        @Path("key") key : String = "524d4b724e6a6f79313037777a6e6377",
        @Path("page") page : String?,
        @Path("total") total : String?
    ):Call<DataVO>

    companion object {
        fun create(): BakeryAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed((request))
            }

            val test = Dispatcher()


            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .dispatcher(test)
                .build()
            //OkHttp: Interceptor 지원
            // 1) Application Interceptor: OkHttpClient.Builder().addInterceptor
            // 2) Network Interceptor: OkHttpClient.Builder().addNetworkInterceptor


            return Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BakeryAPI::class.java)

        }
    }
}