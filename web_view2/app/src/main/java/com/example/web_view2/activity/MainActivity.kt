package com.example.web_view2.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import androidx.core.app.ActivityCompat
import com.example.web_view2.R
import com.example.web_view2.base.BaseApplication
import com.example.web_view2.databinding.ActivityMainBinding
import com.example.web_view2.fragment.SplashFragment
import com.example.web_view2.webview.MyWebViewClient
import com.example.web_view2.webview.MyWebChromeClient
import com.pionnet.overpass.extension.getAppVersion

class MainActivity : AppCompatActivity() {
    private val tag = javaClass.simpleName //log 찍을 때
    private var hash: String? = null //hash
    private var mCurUrl = "" //url을 비워놓네..
    private lateinit var binding: ActivityMainBinding

    private val url = "https://m.sivillage.com/dispctg/initBeautyMain.siv"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addSplashFragment()

        instance = this

        BaseApplication.isAppRunning = true

        //frame layout
        //binding.imageview.loadImageCircle("https://postfiles.pstatic.net/MjAyMTA2MjJfMjkg/MDAxNjI0MzY2NDg2NDk1.tn4jXdVbTjFHpdgGEuAPaflQu62tcijfvyUFa1XRg_0g.fCr7IccRvu9Ykf_6Un5ZhiHKey-lOGn7RZ-5VKmYb7gg.JPEG.joyfuljuli/IMG_4903.JPG?type=w966", 400, 400)

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
        settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            userAgentString = userAgent
        }

        binding.myWebView.loadUrl(url)

        //webview client / webchrome client
        binding.myWebView.webViewClient = MyWebViewClient()
        binding.myWebView.webChromeClient = MyWebChromeClient()

    }
    fun addSplashFragment(){
        val splashFragment = SplashFragment()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.frameLayout_splash, splashFragment, splashFragment.javaClass.simpleName).commit()
    }


    //뒤로가기
    override fun onBackPressed() {
        if (binding.myWebView.canGoBack()) {
            binding.myWebView.goBack()
        }
    }

    fun startCallIntent(tel: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                100 //permission call
            )
        }else{
            if(tel.isNotEmpty()){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(tel))
                startActivity(intent)
            }
        }
    }

    companion object {
        lateinit var instance: MainActivity
    }
}