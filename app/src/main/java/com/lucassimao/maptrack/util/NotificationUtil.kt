package com.lucassimao.maptrack.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.lucassimao.maptrack.util.Constants.NOTIFICATION_CHANNEL_ID
import com.lucassimao.maptrack.util.Constants.NOTIFICATION_CHANNEL_NAME

@RequiresApi(Build.VERSION_CODES.O)
fun initializeNotificationChannel(notificationManager: NotificationManager) {
    val channel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_LOW
    )
    notificationManager.createNotificationChannel(channel)
}