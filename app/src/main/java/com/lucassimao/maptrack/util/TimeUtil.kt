package com.lucassimao.maptrack.util

import java.util.concurrent.TimeUnit

fun calculateElapsedTime(initialTime: Long): Long {
    val currentTime = System.currentTimeMillis()
    return currentTime - initialTime
}

fun getFormattedElapsedTime(totalTimeInMilliseconds: Long): String {
    var milliseconds = totalTimeInMilliseconds
    val hour = TimeUnit.MILLISECONDS.toHours(milliseconds)
    milliseconds -= TimeUnit.HOURS.toMillis(hour)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

    return "${if (hour < 10) "0" else ""}$hour:" +
            "${if (minutes < 10) "0" else ""}$minutes:" +
            "${if (seconds < 10) "0" else ""}$seconds"
}