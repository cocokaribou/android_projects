package com.example.youngin.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.youngin.R
import com.example.youngin.base.BaseActivity
import com.example.youngin.base.BaseApplication
import com.example.youngin.data.SplashResponse
import com.example.youngin.databinding.ActivityMainBinding
import com.example.youngin.fragment.SplashFragment
import com.example.youngin.network.CustomHeaderInterceptor
import com.example.youngin.network.HttpUrl
import com.example.youngin.network.MyAPI
import com.example.youngin.webview.MyWebViewClient
import com.example.youngin.webview.MyWebChromeClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.pionnet.overpass.extension.getAppVersion
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
//    private var hash: String? = null //app signature

    /**
     * init activity, web settings
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestSplash()
        BaseApplication.isAppRunning = true

        /* web settings */
        val settings = binding.myWebView.settings
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            (this as Activity).getString(R.string.user_agent),
            this?.let {
                getAppVersion(it)
            } + ": AOS:"
        )

        binding.myWebView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            userAgentString = userAgent
        }


        /* webview client && web chrome client */
        binding.myWebView.loadUrl(MainActivity.url)
        binding.myWebView.apply {
            webViewClient = MyWebViewClient(this@MainActivity)
            webChromeClient = MyWebChromeClient(this@MainActivity)
        }

    }

    /**
     * return API interface
     */
    private fun getAPIService(): MyAPI {
        val okBuilder = OkHttpClient.Builder()

        val myDispatcher = Dispatcher()
        myDispatcher.maxRequests = 8
        myDispatcher.maxRequestsPerHost = 8

        val headerInterceptor = CustomHeaderInterceptor(this)
        val loggingInterceptor = HttpLoggingInterceptor()
        if (com.example.youngin.BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        okBuilder.apply {
            dispatcher(myDispatcher)

            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)
            if (com.example.youngin.BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
            }

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

    /**
     * request API, add Splash Fragment
     */
    private fun requestSplash() {
        val service: MyAPI = getAPIService()
        val callSplash: Call<ResponseBody> = service.getIntro()
        callSplash.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("$tag", "Fail")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val splashResponse: SplashResponse =
                            Gson().fromJson(
                                response.body()!!.string(),
                                SplashResponse::class.java
                            )
                        SplashResponse.setSplashResponse(splashResponse)
                        addSplashFragment()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Network Err!", Toast.LENGTH_LONG).show()
                        e.stackTrace
                    }
                }
            }
        })
    }

    /**
     * Splash Fragment
     */
    fun addSplashFragment() {
        binding.background.visibility = View.GONE
        splashFragment = SplashFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameLayout_splash, splashFragment, splashFragment.javaClass.simpleName)
            .commit()
    }

    fun removeSplashFragment() {
        Handler(Looper.getMainLooper()).postDelayed({
            val ft = supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ft.remove(splashFragment).commitAllowingStateLoss()

        }, 2000)
    }

    /**
     * onBackPressed()
     */
    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }
    }

    /**
     * goBankPay() activity result
     */
    fun startActivityForResultBankPay(intent: Intent) {

    }

    /**
     * onShowFileChooser() activity result
     */
//    @RequiresApi(Build.VERSION_CODES.O)
    fun startActivityForResultFileChooser(intent: Intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            val results: Array<Uri?>

            try {
                if (intent != null) {
                    val dataString = intent.dataString
                    val clipData = intent.clipData
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                        //콜백.onReceiveValue(results) 이렇게 넘겨주기만 하면 되는데...!!
                        Log.e("filechooser checker!", "$results")
                    }
                }

            } catch (e: IOException) {
                Toast.makeText(this, "업로드 아 실패요~", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * get app signature(hash)
     */
    /*
    private fun getApplicationSignature(packageName: String = this.packageName): List<String> {
        al signatureList: List<String>

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val signature = this.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo

                signatureList = if (signature.hasMultipleSigners()) {
                    signature.apkContentsSigners.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                } else {
                    signature.signingCertificateHistory.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                }
            } else {
                val signature = this.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures
                signatureList = signature.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }
            return signatureList
        } catch (e: CertificateException) {

        }
        return emptyList()
    }*/


    companion object {
        lateinit var splashFragment: SplashFragment
        private var url = HttpUrl.serverUrl + "/dispctg/initBeautyMain.siv"
    }
}