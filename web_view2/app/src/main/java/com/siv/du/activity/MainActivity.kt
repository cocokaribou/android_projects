package com.siv.du.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.siv.du.R
import com.siv.du.base.BaseActivity
import com.siv.du.base.BaseApplication
import com.siv.du.data.SplashResponse
import com.siv.du.databinding.ActivityMainBinding
import com.siv.du.dialog.BasicDialog
import com.siv.du.fragment.SplashFragment
import com.siv.du.network.CustomHeaderInterceptor
import com.siv.du.network.HttpUrl
import com.siv.du.network.MyAPI
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.siv.du.common.RequestCode
import com.siv.du.network.NetworkManager
import com.siv.du.webview.MyWebView
import com.siv.du.webview.MyWebViewClient
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
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

/**
 * 앱 진입점
 * - 웹뷰 로드, 스플래시 프래그먼트 생성 onCreate()
 * - Api 인터페이스 생성 getAPIService()
 * - Api 요청결과로 스플래시 데이터 초기화 requestSplash()
 *
 * - 권한 체크
 * - startActivityForResultBankPay()
 * - startActivityForResultFileChooser()
 */
class MainActivity : BaseActivity(), MyWebView.WebViewScrollListener, MyWebViewClient.WebViewListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var url: String
    private lateinit var telNo: String

    private val permissionCall = Manifest.permission.CALL_PHONE
    private val permissionCamera = Manifest.permission.CAMERA
    private val permissionPush = arrayOf<String>(

    )

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

        binding.background.bringToFront()
        binding.frameLayoutSplash.bringToFront()

        url = HttpUrl.serverUrl + getString(R.string.main)
        binding.webView.loadUrl(url)
//        binding.webView.loadUrl("http://m.sivillage.cttd.co.kr/html/ko/disp/brand_main7.html")
//        binding.webView.loadUrl("http://m.sivillage.cttd.co.kr/html/ko/disp/search_result_v2.html")
//        binding.webView.loadUrl("http://m.sivillage.cttd.co.kr/html/ko/etc/etc_setApp.html")
//        binding.webView.loadUrl("file:///android_asset/test.html")

        initTestButton()
    }

    override fun onResume() {
        super.onResume()

        NetworkManager.getInstance(this)
            .checkNetworkAvailable(object : NetworkManager.OnNetworkListener{
                override fun networkAvailable() {
                }

                override fun finishApp() {
                    BasicDialog.create(BasicDialog.Param.TYPE_NETWORK_ERR)
                    Log.e("여기를 타니", "i wanna know")
                }
            })
    }

    private fun initTestButton() {
        binding.btnTest.setOnClickListener {
            throw RuntimeException("test crash")
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
//        if (com.siv.du.BuildConfig.DEBUG) {
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
//        }
        okBuilder.apply {
            dispatcher(myDispatcher)

            addInterceptor(headerInterceptor)
            addInterceptor(loggingInterceptor)
//            if (com.siv.du.BuildConfig.DEBUG) {
//                addNetworkInterceptor(StethoInterceptor())
//            }

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
                //자꾸 여길 타네....
                Log.e("$tag checker!", "failed to receive intro response")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val splashResponse: SplashResponse =
                            Gson().fromJson(
                                response.body()!!.string(),
                                SplashResponse::class.java
                            )
                        if (!splashResponse.header?.rCode.isNullOrEmpty()) {
                            if (splashResponse.header?.rCode == "9999") {
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
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.frameLayout_splash, splashFragment)
                commit()
            }
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
        binding.btnTest.visibility = View.VISIBLE
    }

    /**
     * onBackPressed()
     */
    override fun onBackPressed() {
        //웹뷰 내에서 새 창 켜져있을 경우
        if (binding.webView.mChromeClient.isChildOpen()) {
            binding.webView.mChromeClient.closeChild()
        }
        //웹뷰 뒤로가기 지원될 경우
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        }
        super.onBackPressed()
        return

    }

    /**
     * goBankPay() activity result
     */
    fun startActivityForResultBankPay(intent: Intent) {

    }

    /* 전화걸기 */
    fun callIntent(tel: String) {
        telNo = tel
        when (checkPermission(permissionCall)) {
            true -> {
                if (telNo.isNotEmpty()) {
                    startActivity(Intent(Intent.ACTION_DIAL, "tel:$telNo".toUri()))
                }
            }
            false -> {
                askPermissions(permissionCall)
            }
        }
    }


    /**
     * file path 콜백 받아와서 파일 업로드
     */
    val startForResultUpload =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val results: Array<Uri>?
                val chromeClient = binding.webView.mChromeClient

                //activity result에 따라 처리해줌
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    try {
                        if (chromeClient.filePathCallbackLollipop == null) {
                            return@registerForActivityResult
                        }
                        val resultData = activityResult.data?.data
                        if (resultData != null) {
                            results = arrayOf(Uri.parse(resultData.toString()))

                            chromeClient.filePathCallbackLollipop!!.onReceiveValue(
                                results
                            )
                            chromeClient.filePathCallbackLollipop = null
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "업로드실패 ", Toast.LENGTH_SHORT).show()
                    }
                } else { //cancel 등등처리
                    if (chromeClient.filePathCallbackLollipop == null) return@registerForActivityResult
                    chromeClient.filePathCallbackLollipop!!.onReceiveValue(null)
                    chromeClient.filePathCallbackLollipop = null
                }
            }
        }

    /**
     * permission 마시멜로우 미만은 승인돼있음
     */
    fun checkPermission(permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun askPermissions(permission: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            var permissionCode = when (permission) {
                permissionCall -> {
                    RequestCode.permissionCallCode
                }
                permissionCamera -> {
                    RequestCode.permissionCameraCode
                }
                else -> {
                    10000
                }
            }

            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                permissionCode
            )
        }
    }

    /**
     * ActivityCompat.requestPermissions의 결과를 처리
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestCode.permissionCameraCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser()
                } else {
                    Log.e("$tag checker!", "camera permission denied")
                    val alert = AlertDialog.Builder(this)
                    alert.setMessage("권한설정을 거부했습니다.").setNegativeButton("확인") { _, _ ->
                    }.show()
                }
                return
            }
            RequestCode.permissionCallCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(Intent.ACTION_DIAL, "tel:$telNo".toUri()))
                } else {
                    Log.e("$tag checker!", "call permission denied")
                    val alert = AlertDialog.Builder(this)
                    alert.setMessage("권한설정을 거부했습니다.").setNegativeButton("확인") { _, _ ->
                    }.show()
                }
            }

            RequestCode.permissionPushCode -> {

            }
            else -> {

            }
        }
        Log.e("$tag checker!", "requestCode: $requestCode")
    }

    private fun showFileChooser(){
        binding.webView.mChromeClient.fileUpload()
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

    override fun onScrollTop(top: Int) {
    }

    override fun onScrollDown(top: Int) {
    }

    override fun onBottomReached() {
    }

    override fun onPageStarted(url: String) {
    }

    override fun onPageShouldOverride(url: String) {
    }

    override fun onPageFinished(webView:WebView, url: String) {
        Log.e("$tag checker!", "webViewClient: onPageFinished")
    }

    override fun call(tel: String) {
        TODO("Not yet implemented")
    }

}