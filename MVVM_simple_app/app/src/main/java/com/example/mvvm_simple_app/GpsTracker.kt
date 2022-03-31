package com.example.mvvm_simple_app

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat

class GpsTracker(private val context: Context) : Service(), LocationListener {
    val location: Location? = null

    private val locationManager : LocationManager by lazy {
        context.getSystemService(LOCATION_SERVICE) as LocationManager
    }

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    companion object {
        const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10f
        const val MIN_TIME_BW_UPDATES: Long = 1000 * 60 * 1
    }

    init {
        getCurrentLocation()
    }

    fun getCurrentLocation(): Location? {
        try {
            val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGPSEnabled || isNetworkEnabled) {
                val hasFineLocationManager = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                val hasCoarseLocationManager = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

                if (hasFineLocationManager != PackageManager.PERMISSION_GRANTED ||
                        hasCoarseLocationManager != PackageManager.PERMISSION_GRANTED) {
                  return null
                }

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this)
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    location?.let {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this)
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                    location?.let {
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                }
            }
        } catch (e:Exception) {
            Log.v("GpsTracker", "Exception occurred ${e.message}")
        }
        return location
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
    }


}