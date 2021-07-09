package com.example.youngin.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.youngin.R
import com.example.youngin.base.BaseActivity
import com.example.youngin.base.BaseApplication
import com.example.youngin.data.SplashResponse
import com.example.youngin.databinding.ActivityMainBinding
import com.example.youngin.dialog.BasicDialog
import com.example.youngin.fragment.SplashFragment
import com.example.youngin.network.CustomHeaderInterceptor
import com.example.youngin.network.HttpUrl
import com.example.youngin.network.MyAPI
import com.example.youngin.webview.MyWebViewClient
import com.example.youngin.webview.MyWebChromeClient
import com.example.youngin.webview.MyWebView
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

/**
 * 앱 진입점
 * - 웹뷰 로드, 스플래시 프래그먼트 생성 onCreate()
 * - Api 인터페이스 생성 getAPIService()
 * - Api 요청결과로 스플래시 데이터 초기화 requestSplash()
 * - startActivityForResultBankPay()
 * - startActivityForResultFileChooser()
 */
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var url: String
//    private var hash: String? = null //app signature

    /**
     * init activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BaseApplication.isAppRunning = true
        requestSplash()

        url = HttpUrl.serverUrl + getString(R.string.main)
        binding.webView.loadUrl(url)

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
     * request API
     * add Splash Fragment
     */
    private fun requestSplash() {
        val service: MyAPI = getAPIService()
        val callSplash: Call<ResponseBody> = service.getIntro()
        callSplash.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("$tag checker!", "failed to receive response")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val splashResponse: SplashResponse =
                            Gson().fromJson(
                                response.body()!!.string(),
                                SplashResponse::class.java
                            )
                        if(!splashResponse.header?.rCode.isNullOrEmpty()){
                            if(splashResponse.header?.rCode == "9999") {
                                showDialog(BasicDialog.create(BasicDialog.Param.TYPE_NOT_APP))
                                return
                            }
                        }
                        SplashResponse.setSplashResponse(splashResponse)
                        addSplashFragment()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    /**
     * add splash fragment
     */
    private fun addSplashFragment() {
        //activity에 하얀 배경 제거
        binding.background.visibility = View.GONE

        splashFragment = SplashFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameLayout_splash, splashFragment, splashFragment.javaClass.simpleName)
            .commit()
    }

    /**
     * remove splash fragment
     *
     */
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
        //웹뷰 내에서 새 창 켜져있을 경우
        if(binding.webView.mChromeClient.isChildOpen()){
            binding.webView.mChromeClient.closeChild()
        }
        //웹뷰 뒤로가기 지원될 경우
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        }
    }

    /**
     * goBankPay() activity result
     */
    fun startActivityForResultBankPay(intent: Intent) {

    }

    /*fun startActivityForResultFileChooser(intent: Intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            val results: Array<Uri>

            try {
                if(binding.webView.mChromeClient?.filePathCallbackLollipop == null){
//                if (MyWebChromeClient.filePathCallbackLollipop == null) {
                    Log.e("$tag checker!", "file path callback null")
                    return
                }
                val data = intent.data
                Log.e("$tag checker!", "intent.data: $data")
                Log.e("$tag checker!", "intent: $intent")
                val dataArr: Array<Uri>?
                if (data != null) {
                    dataArr = arrayOf(Uri.parse(data.toString()))
                    binding.webView.mChromeClient?.filePathCallbackLollipop!!.onReceiveValue(
                        dataArr
                    )
                }
                //onReceiveValue() 처리하고 비워주기
            binding.webView.mChromeClient?.filePathCallbackLollipop = null

            } catch (e: IOException) {
                Toast.makeText(this, "upload failed", Toast.LENGTH_LONG).show()
            }
        }
    }*/

    val startForResultUpload =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val results: Array<Uri>?

                //activity result에 따라 처리해줌
                if (result.resultCode == Activity.RESULT_OK) {
                    try {
                        if (binding.webView.mChromeClient?.filePathCallbackLollipop == null) {
                            return@registerForActivityResult
                        }
                        val resultData = result.data?.data
                        if (resultData != null) {
                            results = arrayOf(Uri.parse(resultData.toString()))

                            binding.webView.mChromeClient?.filePathCallbackLollipop!!.onReceiveValue(
                                results
                            )
                            binding.webView.mChromeClient?.filePathCallbackLollipop = null
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "업로드실패 ", Toast.LENGTH_SHORT).show()
                    }
                } else { //cancel 등등처리
                    if (binding.webView.mChromeClient?.filePathCallbackLollipop == null) return@registerForActivityResult
                    binding.webView.mChromeClient?.filePathCallbackLollipop!!.onReceiveValue(null)
                    binding.webView.mChromeClient?.filePathCallbackLollipop = null
                }
            }
        }

    fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA
                    ), 1001
                )
                return false
            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun askPermissions(){
        requestPermissions(
            arrayOf(
                Manifest.permission.CAMERA
            ), 1001
        )
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
    }
}