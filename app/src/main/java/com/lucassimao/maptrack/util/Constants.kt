package com.lucassimao.maptrack.util

import android.graphics.Color

object Constants {
    const val GOOGLE_MAPS_CAMERA_ZOOM_VALUE = 18f

    const val PERMISSION_REQUEST_CODE = 123

    const val START_OR_RESUME_SERVICE_ACTION = "START_OR_RESUME_SERVICE_ACTION"
    const val PAUSE_SERVICE_ACTION = "PAUSE_SERVICE_ACTION"
    const val STOP_SERVICE_ACTION = "STOP_SERVICE_ACTION"

    const val INTERVAL_TIME = 1000L
    const val FASTEST_INTERVAL_TIME = 500L

    const val ROUTE_LINE_COLOR = Color.RED
    const val ROUTE_LINE_WIDTH = 8f

    const val TIMER_UPDATE_INTERVAL_IN_MILLISECONDS = 50L

    const val INTERVAL_IN_SECOND = 1000L

    const val NOTIFICATION_CHANNEL_ID = "map_track_channel_id"
    const val NOTIFICATION_CHANNEL_NAME = "map_track_channel_name"
    const val NOTIFICATION_ID = 1

    const val TRACKING_FRAGMENT_ACTION = "TRACKING_FRAGMENT_ACTION"

    const val REQUEST_CODE_PENDING_INTENT_MODULE = 0
    const val REQUEST_CODE_PENDING_INTENT_PAUSE = 1
    const val REQUEST_CODE_PENDING_INTENT_RESUME = 2
}