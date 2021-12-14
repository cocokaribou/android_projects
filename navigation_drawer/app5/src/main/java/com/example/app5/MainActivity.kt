package com.example.app5

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.app5.databinding.ActivityMainBinding
import androidx.fragment.app.add
import androidx.fragment.app.commit
import java.security.MessageDigest
import android.content.pm.PackageInfo
import android.util.Base64
import com.example.app5.ui.home.HomeFragment
import java.security.NoSuchAlgorithmException

import com.example.app5.ui.login.LoginFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LoginFragment>(R.id.container, args = bundleOf())
            }
        }
        getHashKey()
    }

    val addHomeFrag = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        Log.e("youngin", activityResult.resultCode.toString())
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<HomeFragment>(R.id.container, args = bundleOf())
                }
            }
        }
    }

    val startForGoogleLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        Log.e("youngin", activityResult.resultCode.toString())
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }

    val startForCustomLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        when (activityResult.resultCode) {
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}