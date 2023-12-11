package com.lucassimao.maptrack.data.entity

import android.location.Location
import com.lucassimao.maptrack.data.core.ListOfLocations
import javax.inject.Inject

class DistanceTraveledEntity @Inject constructor(
    private val metersToKilometersConverterEntity: MetersToKilometersConverterEntity
) {
    fun calculateTotalDistanceTraveled(routePolylines: List<ListOfLocations>): Float {
        var totalDistance = 0f
        for (route in routePolylines) {
            totalDistance += calculateRouteDistance(route)
        }
        return metersToKilometersConverterEntity.metersToKilometers(totalDistance)
    }

    private fun calculateRouteDistance(route: ListOfLocations): Float {
        var distance = 0f
        val distanceArray = FloatArray(1)
        for (index in 0 until route.size - 1) {
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
}