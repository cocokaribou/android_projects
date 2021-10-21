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
        @Path("key") key:String = BuildConfig.API_KEY,
        @Path("page") page : String? = "1",
        @Path("total") total : String? = "100"
    ):Call<DataVO>

    //retrofit annotation -> http method를 이용


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



            //TODO 오브젝트 구조 파싱해서 바꿔야함

            return Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BakeryAPI::class.java)



            //Retrofit: OkHttp를 네트워킹 계층으로 활용하며 그 위에 구축된다.
            //JSON 응답을 사전에 정의된 POJO를 통해 직렬화 할 수 있다.

        }
    }
}