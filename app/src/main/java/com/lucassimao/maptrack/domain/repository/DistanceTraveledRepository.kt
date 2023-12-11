package com.lucassimao.maptrack.domain.repository

import com.lucassimao.maptrack.data.core.ListOfLocations

interface DistanceTraveledRepository {
    fun calculateTotalDistanceTraveled(routePolylines: List<ListOfLocations>): Float
}