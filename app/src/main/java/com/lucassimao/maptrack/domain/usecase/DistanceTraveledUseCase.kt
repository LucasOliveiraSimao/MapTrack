package com.lucassimao.maptrack.domain.usecase

import com.lucassimao.maptrack.data.core.ListOfLocations
import com.lucassimao.maptrack.domain.repository.DistanceTraveledRepository
import javax.inject.Inject

class DistanceTraveledUseCase @Inject constructor(
    private val distanceTraveledRepository: DistanceTraveledRepository
) {
    fun execute(routePolylines: List<ListOfLocations>): Float {
        return distanceTraveledRepository.calculateTotalDistanceTraveled(routePolylines)
    }
}