package com.example.web_view2.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.web_view2.R
import com.example.web_view2.base.BaseApplication
import com.example.web_view2.databinding.ActivityMainBinding
import com.example.web_view2.fragment.SplashFragment
import com.example.web_view2.webview.MyWebViewClient
import com.example.web_view2.webview.MyWebChromeClient
import com.pionnet.overpass.extension.getAppVersion
import com.pionnet.overpass.extension.bytesToHex
import com.pionnet.overpass.extension.loadImage
import com.pionnet.overpass.extension.loadImageCircle
import java.security.MessageDigest
import java.security.cert.CertificateException

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName //log 찍을 때
    private var hash: String? = null //hash
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance = this

        val hashArray = getApplicationSignature(this.packageName)
        if (hashArray.isNotEmpty()) {
            hash = hashArray[0]
        } else {
            hash = "1234"
        }

        addSplashFragment()
        removeSplashFragment()

        BaseApplication.isAppRunning = true

        //web settings
        val settings = binding.myWebView.settings
        val userAgent = String.format(
            "%s SIV_SEARCH: %s %s",
            settings.userAgentString,
            instance?.let { it.getString(R.string.user_agent) },
            instance?.let {
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

    private fun addSplashFragment() {
        splashFragment = SplashFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameLayout_splash, splashFragment, splashFragment.javaClass.simpleName)
            .commit()
    }

    private fun removeSplashFragment() {
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

    //webChromeClient filechooser
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            12 -> {
                Log.e("로그나 찍어", "냠냠")
            }
            10008 -> {
                Log.e("$tag","메인으로 잘 빠집니다")
            }
        }
//        REQUEST_FI
//        https://www.blueswt.com/118
    }

    //api 요청시 post 필드에 넣을 appHash
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
    }


    companion object {
        lateinit var instance: MainActivity
        lateinit var splashFragment: SplashFragment
        private const val url = "https://m.sivillage.com/dispctg/initBeautyMain.siv"

    }
}