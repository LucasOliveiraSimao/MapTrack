package com.lucassimao.maptrack.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.util.Constants.FASTEST_INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.INTERVAL_IN_SECOND
import com.lucassimao.maptrack.util.Constants.INTERVAL_TIME
import com.lucassimao.maptrack.util.Constants.NOTIFICATION_ID
import com.lucassimao.maptrack.util.Constants.PAUSE_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.REQUEST_CODE_PENDING_INTENT_PAUSE
import com.lucassimao.maptrack.util.Constants.REQUEST_CODE_PENDING_INTENT_RESUME
import com.lucassimao.maptrack.util.Constants.START_OR_RESUME_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.STOP_SERVICE_ACTION
import com.lucassimao.maptrack.util.Constants.TIMER_UPDATE_INTERVAL_IN_MILLISECONDS
import com.lucassimao.maptrack.util.ListOfPolylines
import com.lucassimao.maptrack.util.PermissionUtil.hasLocationPermissions
import com.lucassimao.maptrack.util.calculateElapsedTime
import com.lucassimao.maptrack.util.getFormattedElapsedTime
import com.lucassimao.maptrack.util.initializeNotificationChannel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NavigationMapTrackService : LifecycleService() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var baseNotification: NotificationCompat.Builder

    private var isFirstTimeRun = true
    private var startTimeInMilliseconds = 0L
    private var previousTime = 0L
    private var currentElapsedTime = 0L
    private var lastSecondTime = 0L

    private var isServiceKilled = false

    companion object {
        val listOfPolylinesLiveData = MutableLiveData<ListOfPolylines>()
        var isTrackingLiveData = MutableLiveData<Boolean>()
        var totalExecutionTimeLiveData = MutableLiveData<Long>()
    }

    override fun onCreate() {
        super.onCreate()

        publishInitialValues()

        isTrackingLiveData.observe(this) {
            startTracking(it)
            updateNotificationTrackingStatus(it)
        }
    }

    private fun publishInitialValues() {
        isTrackingLiveData.postValue(false)
        listOfPolylinesLiveData.postValue(mutableListOf())
        totalExecutionTimeLiveData.postValue(0L)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                START_OR_RESUME_SERVICE_ACTION -> {

                    if (isFirstTimeRun) {
                        startForegroundService()
                        isFirstTimeRun = false
                    } else {
                        initializeTimer()
                    }

                }

                PAUSE_SERVICE_ACTION -> {
                    pauseTracking()
                }

                STOP_SERVICE_ACTION -> {
                    stopTracking()
                }

            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun initializeTimer() {

        createEmptyPolylines()
        isTrackingLiveData.postValue(true)
        startTimeInMilliseconds = System.currentTimeMillis()

        CoroutineScope(Dispatchers.Main).launch {
            while (isTrackingLiveData.value!!) {

                currentElapsedTime = calculateElapsedTime(startTimeInMilliseconds)
                val totalTime = previousTime + currentElapsedTime

                updateTotalExecutionTime(totalTime)

                delay(TIMER_UPDATE_INTERVAL_IN_MILLISECONDS)
            }
            previousTime += currentElapsedTime
        }
    }

    private fun updateNotificationTrackingStatus(isTracking: Boolean) {

        val notificationActionLabel =
            if (isTracking) getString(R.string.pause) else getString(R.string.restart)

        val trackingPendingIntent = if (isTracking) {
            val pauseIntentAction = Intent(this, NavigationMapTrackService::class.java).apply {
                action = PAUSE_SERVICE_ACTION
            }
            PendingIntent.getService(
                this,
                REQUEST_CODE_PENDING_INTENT_PAUSE,
                pauseIntentAction,
                FLAG_UPDATE_CURRENT or FLAG_MUTABLE
            )
        } else {
            val resumeIntentAction = Intent(this, NavigationMapTrackService::class.java).apply {
                action = START_OR_RESUME_SERVICE_ACTION
            }
            PendingIntent.getService(
                this,
                REQUEST_CODE_PENDING_INTENT_RESUME,
                resumeIntentAction,
                FLAG_UPDATE_CURRENT or FLAG_MUTABLE
            )
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        baseNotification.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(baseNotification, ArrayList<NotificationCompat.Action>())
        }

        if (!isServiceKilled) {
            baseNotification.addAction(
                R.drawable.ic_pause,
                notificationActionLabel,
                trackingPendingIntent
            )

            notificationManager.notify(NOTIFICATION_ID, baseNotification.build())
        }

    }

    private fun startForegroundService() {

        initializeTimer()
        isTrackingLiveData.postValue(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initializeNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotification.build())

        totalExecutionTimeLiveData.observe(this) {

            if (!isServiceKilled) {
                baseNotification.setContentText(getFormattedElapsedTime(it))
                notificationManager.notify(NOTIFICATION_ID, baseNotification.build())
            }

        }
    }

    private fun updateTotalExecutionTime(totalTime: Long) {
        totalExecutionTimeLiveData.postValue(totalTime)

        if (totalExecutionTimeLiveData.value!! >= lastSecondTime + INTERVAL_IN_SECOND) {
            totalExecutionTimeLiveData.postValue(totalExecutionTimeLiveData.value!! + 1)
            updateLastSecondTime()
        }
    }

    private fun updateLastSecondTime() {
        lastSecondTime += INTERVAL_IN_SECOND
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
        isServiceKilled = true
        isFirstTimeRun = true
        publishInitialValues()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

}