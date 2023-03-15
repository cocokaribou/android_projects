package com.example.elandmall_kotlin

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_LINK_EVENT
import com.example.elandmall_kotlin.common.CommonConst.EXTRA_SEARCH_TAB
import com.example.elandmall_kotlin.common.CommonConst.SEARCH_BRAND
import com.example.elandmall_kotlin.common.CommonConst.SEARCH_POPULAR
import com.example.elandmall_kotlin.databinding.LayoutBottomBarBinding
import com.example.elandmall_kotlin.databinding.LayoutTopBarBinding
import com.example.elandmall_kotlin.ui.capture.CaptureActivity
import com.example.elandmall_kotlin.ui.goods.GoodsActivity
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuActivity
import com.example.elandmall_kotlin.ui.intro.IntroActivity
import com.example.elandmall_kotlin.ui.search.SearchActivity
import com.example.elandmall_kotlin.ui.webview.WebViewActivity
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.PermissionGrantHelper
import com.example.elandmall_kotlin.util.dialogConfirm
import com.example.elandmall_kotlin.util.isNetworkAvailable

open class BaseActivity : AppCompatActivity() {

    private val permissionHelper = PermissionGrantHelper(this)
    private val imagePermissions = arrayOf(
        Manifest.permission.CAMERA
    )

    fun navToSearchCamera() {
        if (isNetworkAvailable(this)) {
            if (permissionHelper.isAllGranted(imagePermissions)) {
                startActivity(Intent(this, CaptureActivity::class.java))
            } else {
                dialogConfirm(this, "카메라 및 사진/미디어에 엑세스 하도록 접근 권한을 허용해야 합니다.",
                    okListener = {
                        if (permissionHelper.isAllGranted(imagePermissions)) {
                            startActivity(Intent(this, CaptureActivity::class.java))
                        } else {
                            val rationales = imagePermissions.filterIndexed { index, s ->
                                ActivityCompat.shouldShowRequestPermissionRationale(this, s)
                            }
                            if (rationales.isNotEmpty()) {
                                startActivity(
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null))
                                )
                            } else {
                                permissionHelper.checkMultiplePermissionsAndAction(imagePermissions,
                                    grantedCallback = {
                                        Logger.d("all granted.")
                                    }
                                )
                            }
                        }
                    }
                )
            }
        } else {
            //dialogAlert(this, getString(R.string.msg_network_error))
        }
    }

    private val resultCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startActivity(Intent(this, CaptureActivity::class.java))
        } else {

            dialogConfirm(this, "카메라 및 사진/미디어에 엑세스 하도록 접근 권한을 허용해야 합니다.",
                okListener = {
                    val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                    if (rationale) {
                        startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                .setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null))
                        )
                    } else {
                    }
                }
            )
            onPermissionDenied()
        }
    }

    protected fun isSavedInstanceState(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            val intent = Intent(this, IntroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finishAffinity()
            true
        } else {
            false
        }
    }

    protected open fun onLinkEvent(event: LinkEvent) {
        when (event.type) {
            LinkEventType.HOME -> navToHome()
            LinkEventType.LNB -> navToLNB()
            LinkEventType.DEFAULT -> navToWeb(event.url)
            LinkEventType.SEARCH -> navToSearch(event.data)
            LinkEventType.CAPTURE -> navToCapture()
            LinkEventType.GOODS -> navToGoods()
            else -> {}
        }
    }

    fun initTopBar(topBar: LayoutTopBarBinding) = with(topBar) {
        menuIc.setOnClickListener {
            navToLNB()
        }
        searchInput.setOnClickListener {
            navToSearch(SEARCH_POPULAR)
        }
    }

    fun initBottomBar(bottomBar: LayoutBottomBarBinding) = with(bottomBar) {
        btn1.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.LNB))
        }
        btn2.setOnClickListener {
            EventBus.fire(LinkEvent(LinkEventType.SEARCH, SEARCH_BRAND))
        }
        home.setOnClickListener { }
        btn3.setOnClickListener { }
        btn4.setOnClickListener { }
    }

    private fun navToHome() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        })
    }

    private fun navToWeb(url: String?) {
        startActivity(Intent(this, WebViewActivity::class.java))
    }

    private fun navToLNB() {
        if (isNetworkAvailable(this)) {
            startActivity(Intent(this, LeftMenuActivity::class.java))
        } else {

        }
    }

    private fun navToCart() {}

    private fun navToSearch(data: String?) {
        if (isNetworkAvailable(this)) {
            val intent = Intent(this, SearchActivity::class.java)
                .putExtra(EXTRA_SEARCH_TAB, data)
            startActivity(intent)
        } else {

        }
    }

    private fun navToCapture() {
        resultCameraPermission.launch(Manifest.permission.CAMERA)
    }

    private fun navToGoods() {
        startActivity(Intent(this, GoodsActivity::class.java))
    }

    open fun onPermissionDenied() {}

    private fun navToSetting() {}
}