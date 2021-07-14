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
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.Toast
import androidx.activity.result.ActivityResult
import com.siv.du.common.CommonConst
import com.siv.du.common.RequestCode
import com.siv.du.dialog.BasicDialog
import java.lang.Exception

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
                RequestCode.permissionCallCode
            )
            //권한 여부 다시 확인해서 리턴
            return (ContextCompat.checkSelfPermission(
                this,
                permissionCall
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return true
    }


    fun showDialog(fragment: BasicDialog) {
        val tag = fragment.javaClass.simpleName
        val manager = supportFragmentManager

        fragment.isCancelable = false
        fragment.show(manager, tag)
    }
}