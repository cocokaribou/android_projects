package com.example.mvvm_simple_app

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_simple_app.model.WeatherInfo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val weatherObserver = Observer<WeatherInfo> { weather ->
        weather?.let {

        } ?: run {

        }
    }

    // location permission request
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all {permission -> permission.value == true}) {
//                val userNowLocation = (activity as MainActivity).locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                val uLatitude = userNowLocation?.latitude ?: 35.898054
//                val uLongitude = userNowLocation?.longitude ?: 128.544296
            } else {
                // do something
            }
        }

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val gpsTracker: GpsTracker by lazy {
        GpsTracker(this)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentWeather.observe(this, weatherObserver)

        if (checkLocationServiceStatus()) {
            checkRunTimePermission()
        } else {
            showDialog(msg= "위치서비스를 활성화시켜주세요.", posFun = {})
        }

        locationPermissionRequest.launch(permissions)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
//            fusedLocationClient.getCurrentLocation()
            return
        }

    }

    private fun checkLocationServiceStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkRunTimePermission() {

    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {

            }
        }
    }


    private fun showDialog(msg: String, posFun: (()->Any)? = null, negFun: (()->Any)? = null) {
        val dialog = AlertDialog.Builder(this).setMessage(msg)
        posFun?.let {
            val listener = DialogInterface.OnClickListener { _, _ -> it.invoke() }
            dialog.setPositiveButton("확인", listener)
        }
        negFun?. let {
            val listener = DialogInterface.OnClickListener { dialog, _ ->
                it.invoke()

            }
            dialog.setNegativeButton("취소", listener)
        }
        dialog.show()
    }


    // TODO
    // 앱이 백에 있다가 프론트로 올라올때마다 위치정보 읽어오고 -> 날씨 정보에 toss
    override fun onResume() {
        super.onResume()
        if (checkLocationServiceStatus()) {

        } else {

        }
    }
}