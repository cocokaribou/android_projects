package kr.co.pionnet.SampleProject

import android.net.Uri
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface MyAPI {
    @Multipart
    @POST("/app/registFcmToken")
    fun registerTokenApi(
        @Part(value = "token") token: String,
        @Part("target_app") target_app: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/app/pushOpen")
    fun checkPushOpen(
        @Field(value="transaction_id") transaction_id: String
    ): Call<ResponseBody>

    companion object {
        fun getAPIService(): MyAPI {
            return Retrofit.Builder()
                .baseUrl(CommonConst.api_baseurl)
                .client(getUnsafeOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create()) // convert string to request body
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

                // client builder
                val builder = OkHttpClient.Builder()
                builder.apply {
                    // socket factory
                    sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)

                    // hostname verifier
                    hostnameVerifier{ hostname, _ ->
                        val baseUri = Uri.parse(CommonConst.api_baseurl)
                        (hostname.equals(baseUri.host, ignoreCase = true))
                    }

                    // dispatcher
                    val mDispatcher = Dispatcher()
                    mDispatcher.maxRequests = 8
                    mDispatcher.maxRequestsPerHost = 8
                    dispatcher(mDispatcher)

                    // logging interceptor
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(loggingInterceptor)

                    // header interceptor
                    addInterceptor(Interceptor {
                        val request = it.request().newBuilder()
                            .addHeader("biz_id", CommonConst.biz_id)
                            .addHeader("app_id", CommonConst.app_id)
//                            .addHeader("content-type", CommonConst.content_type)
                            .build()
                        return@Interceptor it.proceed(request)
                    })
                    connectTimeout(10, TimeUnit.SECONDS)
                    readTimeout(10, TimeUnit.SECONDS)
                }
                builder.build()
            } catch (e: java.lang.Exception) {
                throw RuntimeException(e)
            }
        }
    }
}