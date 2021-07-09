package com.example.youngin.base

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.youngin.network.CustomHeaderInterceptor
import com.example.youngin.network.HttpUrl
import com.example.youngin.network.MyAPI
import com.facebook.stetho.okhttp3.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pionnet.overpass.extension.hasPermission
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.Manifest
import com.example.youngin.dialog.BasicDialog

/**
 * 액티비티 공통기능 구현
 */
open class BaseActivity : AppCompatActivity() {
    var mLandingInfo: String? = null
    var mLandingType = 0

    val tag = javaClass.simpleName
    private val permissionCall = Manifest.permission.CALL_PHONE
    private val permissionCamera = Manifest.permission.CAMERA
    private val permissionCallCode = 100
    private val permissionCameraCode = 101


    /* 전화걸기 */
    fun callIntent(url: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permissionCall
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permissionCall),
                permissionCallCode
            )
        } else {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
            startActivity(intent)
        }
    }

    /* TODO 카메라 권한 확인 */

    fun checkCameraPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permissionCamera
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permissionCamera),
                    permissionCameraCode
                )
                return false
            }
        }
        return true
    }

    //아아... 이거구나
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.e("$tag checker!", "permission granted")
            } else {
                Log.e("$tag checker!", "permission denied")
            }
        }

    fun requestPermission(): Boolean{
        when{
            //permission is already granted
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED ->{
                Log.e("$tag checker!", "permission granted")
                return true
            }

            //permission hasn't been asked yet
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
                return false
            }

        }
    }

    fun showRequestPermission(){
        //show request permission rationale
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.CAMERA
        )
    }

    fun showDialog(fragment: BasicDialog) {
        val tag = fragment.javaClass.simpleName
        val manager = supportFragmentManager

        fragment.isCancelable = false
        fragment.show(manager, tag)
    }
}