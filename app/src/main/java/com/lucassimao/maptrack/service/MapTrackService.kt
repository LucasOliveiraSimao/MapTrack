package com.lucassimao.maptrack.service

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Looper
import androidx.lifecycle.LifecycleService
import com.google.android.gms.location.*
import com.lucassimao.maptrack.util.Constants.FASTEST_INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.STOP_SERVICE_ACTION
import com.lucassimao.maptrack.util.PermissionHelper.hasLocationPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapTrackService : LifecycleService() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var isTracking = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                START_OR_RESUME_SERVICE_ACTION -> {
                    startTracking()
                }

                STOP_SERVICE_ACTION -> {
                    stopTracking()
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (isTracking) {
                locationResult.locations.let {
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        isTracking = true
        if (hasLocationPermissions(this)) {
            fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest.create().apply {
                    interval = INTERVAL_TIME
                    fastestInterval = FASTEST_INTERVAL_TIME
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                },
                locationCallback,
                Looper.getMainLooper()
            )
        }

    }

    private fun stopTracking() {
        isTracking = false
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}