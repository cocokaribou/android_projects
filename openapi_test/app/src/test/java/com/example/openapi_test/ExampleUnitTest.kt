package com.example.openapi_test

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.openapi_test.Data.DataVO
import io.reactivex.Observable
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import org.junit.Assert.*
import org.junit.jupiter.api.DisplayName
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*
import java.util.concurrent.TimeUnit

private const val FAKE_STRING = "Hello World"

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @DisplayName("bakery api")
    @Test
    fun bakeryApiTest() {

        val bakeryService = BakeryClient.retrofit.create(BakeryAPIRx::class.java)

        val source = bakeryService.getBakery()
        source.doOnNext {
//            System.out.println("source왔는가"+it)
        }.test()
            .awaitDone(1, TimeUnit.MILLISECONDS)
            .assertValue{ data ->
//                System.out.println("data왔는가"+data)
                data.voObject.listCount > 0
            }
            .assertComplete()
    }

    object BakeryClient {
        val retrofit: Retrofit

        init {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed((request))
            }

            val mDispatcher = Dispatcher()
            val mConnectionPool = ConnectionPool()

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .dispatcher(mDispatcher)
                .connectionPool(mConnectionPool)
                .build()
            //OkHttp: Interceptor 지원
            // 1) Application Interceptor: OkHttpClient.Builder().addInterceptor
            // 2) Network Interceptor: OkHttpClient.Builder().addNetworkInterceptor


            //TODO 오브젝트 구조 파싱해서 바꿔야함

            retrofit= Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // HTTP response를 rx observable로 변환
                .build()

            //Retrofit: OkHttp를 네트워킹 계층으로 활용하며 그 위에 구축된다.
            //JSON 응답을 사전에 정의된 POJO를 통해 직렬화 할 수 있다.

        }


    }
    interface BakeryAPIRx {

        @GET("${BuildConfig.API_KEY}/json/LOCALDATA_072218_GS/1/100")
        fun getBakery(): Observable<DataVO>
    }
}