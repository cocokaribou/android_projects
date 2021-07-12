package com.siv.du.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.siv.du.dialog.BasicDialog

/**
 * 액티비티 공통기능 구현
 *
 * - 권한 요청
 */
open class BaseActivity : AppCompatActivity() {
    var mLandingInfo: String? = null
    var mLandingType = 0

    val tag = javaClass.simpleName
    private val permissionCall = Manifest.permission.CALL_PHONE
    private val permissionCamera = Manifest.permission.CAMERA
    private val permissionCallCode = 100
    private val permissionCameraCode = 101

    lateinit var telNo:String

    /* 전화걸기 */
    fun callIntent(url: String) {
        when (checkCallPermission()) {
            true -> {
                if (url.isNullOrEmpty()) {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse(url)))
                }
            }
            false -> {

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                permissionCallCode -> {

                }
                permissionCameraCode -> {
                    Log.e("$tag checker!", "camera permissions")
                }
            }

        }
    }

    /* TODO 카메라 권한 확인 */

    private fun checkCallPermission(): Boolean {
        //권한이 없다면
        if (ContextCompat.checkSelfPermission(
                this,
                permissionCall
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한 요청 보낸후
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permissionCall),
                permissionCallCode
            )
            //권한 여부 다시 확인해서 리턴
            return (ContextCompat.checkSelfPermission(
                this,
                permissionCall
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return true
    }

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
                if (ContextCompat.checkSelfPermission(
                        this,
                        permissionCamera
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    checkCameraPermission()
                } else {

                }
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

    fun requestPermission(): Boolean {
        when {
            //permission is already granted
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
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

    fun showRequestPermission() {
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