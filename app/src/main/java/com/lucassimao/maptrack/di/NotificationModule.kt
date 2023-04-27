package com.lucassimao.maptrack.di

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.ui.MainActivity
import com.lucassimao.maptrack.util.Constants.NOTIFICATION_CHANNEL_ID
import com.lucassimao.maptrack.util.Constants.REQUEST_CODE_PENDING_INTENT_MODULE
import com.lucassimao.maptrack.util.Constants.TRACKING_FRAGMENT_ACTION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object NotificationModule {

    @ServiceScoped
    @Provides
    fun providePendingIntent(
        @ApplicationContext context: Context
    ): PendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE_PENDING_INTENT_MODULE,
        Intent(context, MainActivity::class.java).also { intent ->
            intent.action = TRACKING_FRAGMENT_ACTION
        },
        FLAG_UPDATE_CURRENT or FLAG_MUTABLE
    )

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(
        context,
        NOTIFICATION_CHANNEL_ID,
    )
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_run)
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}