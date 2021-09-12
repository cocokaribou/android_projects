package com.pionnet.overpass.push_test

import android.net.Uri
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface MyAPI {
    @GET("/token/regist")
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
                    .baseUrl("http://10.74.105.65:22121")
                    .client(getUnsafeOkHttpClient()!!)
                    .build()
                    .create(MyAPI::class.java)
        }
        private fun getUnsafeOkHttpClient(): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                                try {
                                    chain!![0]!!.checkValidity()
                                } catch (e: java.lang.Exception) {
                                    throw CertificateException("Certificate not valid or trusted.")
                                }
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                )

                // Install the all-trusting trust manager
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()

                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, _ ->
                    val baseUri = Uri.parse("http://10.74.105.65:22121")
                    (hostname.equals(baseUri.host, ignoreCase = true))
                }
                builder.build()
            } catch (e: java.lang.Exception) {
                throw RuntimeException(e)
            }
        }
    }
}