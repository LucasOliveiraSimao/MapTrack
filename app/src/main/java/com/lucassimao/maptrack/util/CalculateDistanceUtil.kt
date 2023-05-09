package com.lucassimao.maptrack.util

import android.location.Location
import com.lucassimao.maptrack.util.Constants.METERS_TO_KILOMETERS_CONVERSION_FACTOR

fun calculateRouteDistance(route: ListOfLocations): Float {
    var distance = 0f
    val distanceArray = FloatArray(1)

    for (index in 0..route.size - 2) {
        val startPoint = route[index]
        val endPoint = route[index + 1]

        Location.distanceBetween(
            startPoint.latitude,
            startPoint.longitude,
            endPoint.latitude,
            endPoint.longitude,
            distanceArray
        )

        distance += distanceArray[0]
    }

    return distance
}

fun metersToKilometers(distanceInMeters: Float): Float {
    return (distanceInMeters / METERS_TO_KILOMETERS_CONVERSION_FACTOR)
}

fun formatFloatToTwoDecimalPlaces(value: Float): String {
    return String.format("%.2f", value)
}