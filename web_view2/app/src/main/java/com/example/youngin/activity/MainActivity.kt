package com.example.youngin.activity

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.*
import android.widget.Toast
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
import java.lang.Exception
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
//    private val tag = javaClass.simpleName //log 찍을 때

    //    private var hash: String? = null //hash
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestSplash()
        BaseApplication.isAppRunning = true


        //web settings
        val settings = binding.myWebView.settings
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            this.getString(R.string.user_agent),
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

        binding.myWebView.loadUrl(url)
        binding.myWebView.apply {
            webViewClient = MyWebViewClient(this@MainActivity)
            webChromeClient = MyWebChromeClient(this@MainActivity)
        }


    }

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
                        SplashResponse.setSplashResponse(splashResponse) //이거 안 해도 gson으로 초기화되지 않나..?
                        addSplashFragment()
                    } catch (e: Exception) {
                        val toast =
                            Toast.makeText(this@MainActivity, "Network Err!", Toast.LENGTH_LONG)
                        toast.show()
                        e.stackTrace
                    }
                }
            }
        })

    }

    fun addSplashFragment() {
        splashFragment = SplashFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameLayout_splash, splashFragment, splashFragment.javaClass.simpleName)
            .commit()
    }

    fun removeSplashFragment() {
        binding.imageview.visibility = View.GONE
        Handler(Looper.getMainLooper()).postDelayed({
            val ft = supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            ft.remove(splashFragment).commitAllowingStateLoss()

        }, 2000)
    }

    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }
    }

    /*//api 요청시 post 필드에 넣을 appHash
    private fun getApplicationSignature(packageName: String = this.packageName): List<String> {
        val signatureList: List<String>

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
        private const val url = "https://m.sivillage.com/dispctg/initBeautyMain.siv"
    }
}