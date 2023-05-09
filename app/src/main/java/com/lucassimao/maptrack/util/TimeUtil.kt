package com.lucassimao.maptrack.util

import com.lucassimao.maptrack.util.Constants.MILLISECONDS_TO_SECONDS_CONVERSION_FACTOR
import com.lucassimao.maptrack.util.Constants.MINUTES_TO_HOURS_CONVERSION_FACTOR
import com.lucassimao.maptrack.util.Constants.SECONDS_TO_MINUTES_CONVERSION_FACTOR
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

fun millisToHours(totalTimeInMilliseconds: Long): Float {
    return totalTimeInMilliseconds /
            MILLISECONDS_TO_SECONDS_CONVERSION_FACTOR /
            SECONDS_TO_MINUTES_CONVERSION_FACTOR /
            MINUTES_TO_HOURS_CONVERSION_FACTOR
}

fun formatDate(timeInMillis: Long): String {
    val pattern = "dd/MM/yy"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(Date(timeInMillis))
}