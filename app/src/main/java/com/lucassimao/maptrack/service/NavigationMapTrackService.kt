package com.lucassimao.maptrack.service

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.util.Constants.FASTEST_INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.PAUSE_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.STOP_SERVICE_ACTION
import com.lucassimao.maptrack.util.ListOfPolylines
import com.lucassimao.maptrack.util.PermissionUtil.hasLocationPermissions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationMapTrackService : LifecycleService() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        val listOfPolylinesLiveData = MutableLiveData<ListOfPolylines>()
        var isTrackingLiveData = MutableLiveData<Boolean>()
    }

    private fun publishInitialValues() {
        isTrackingLiveData.postValue(false)
        listOfPolylinesLiveData.postValue(mutableListOf())
    }

    override fun onCreate() {
        super.onCreate()
        publishInitialValues()

        isTrackingLiveData.observe(this) {
            startTracking(it)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                START_OR_RESUME_SERVICE_ACTION -> {
                    isTrackingLiveData.postValue(true)
                    startTracking(true)
                }

                PAUSE_SERVICE_ACTION -> {
                    pauseTracking()
                }

                STOP_SERVICE_ACTION -> {
                    stopTracking()
                }

                else -> {}
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("MissingPermission")
    private fun startTracking(isTracking: Boolean) {
        createEmptyPolylines()
        if (isTracking) {
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
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (isTrackingLiveData.value!!) {
                locationResult.locations.let { routeLocation ->
                    for (location in routeLocation) {
                        addLocationToPath(location)
                    }
                }
            }
        }

    }

    private fun addLocationToPath(location: Location) {
        val userLocation = LatLng(location.latitude, location.longitude)
        listOfPolylinesLiveData.value?.apply {
            last().add(userLocation)
            listOfPolylinesLiveData.postValue(this)
        }
    }

    private fun createEmptyPolylines() {
        listOfPolylinesLiveData.value?.apply {
            add(mutableListOf())
            listOfPolylinesLiveData.postValue(this)
        } ?: listOfPolylinesLiveData.postValue(mutableListOf(mutableListOf()))
    }

    private fun pauseTracking() {
        isTrackingLiveData.postValue(false)
    }

    private fun stopTracking() {
        isTrackingLiveData.postValue(false)
        pauseTracking()
        publishInitialValues()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}